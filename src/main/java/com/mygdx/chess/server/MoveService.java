package com.mygdx.chess.server;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

class MoveService {
    private final MoveValidator moveValidator;
    private final ChessPieceRepository repository;
    private final ChessPieceFactory chessPieceFactory;
    private MoveDto move;
    private MoveReport moveReport;
    private ChessPieceColor activeColor = ChessPieceColor.WHITE;

    /**
     * Constructor for the MoveService class.
     *
     * @param repository        The repository of chess pieces.
     * @param chessPieceFactory The factory for creating chess pieces.
     */
    MoveService(ChessPieceRepository repository, ChessPieceFactory chessPieceFactory) {
        this.repository = repository;
        this.moveValidator = new MoveValidator(repository);
        this.chessPieceFactory = chessPieceFactory;
    }

    /**
     * Handles a move in the chess game.
     *
     * @param move The move to be made.
     * @return The report of the move.
     */
    MoveReport move(MoveDto move) {
        this.move = move;
        moveReport = new MoveReport(move.inUse());
        if (activeColor != move.inUse().getColor()) {
            return moveReport;
        }
        if (!moveValidator.canMove(move)) {
            return moveReport;
        }
        moveReport.setValid();
        capturePiece();
        moveRookBeforeKing();
        move.inUse().move(move.x(), move.y());
        pawnPromotion();
        checkStalemateOrCheckMate();
        activeColor = moveReport.getNextColor();
        return moveReport;
    }

    /**
     * Returns the color of the active player.
     *
     * @return The color of the active player.
     */
    ChessPieceColor getActiveColor() {
        return activeColor;
    }

    /**
     * This method checks for stalemate or checkmate conditions after a move has been made.
     * Stalemate is a situation in chess where the player whose turn it is to move is not in check but has no legal move.
     * Checkmate is a situation in chess where a player's king is in a position to be captured (in "check") and there is
     * no way to move the king out of capture (the king is "mated").
     * The method first retrieves the color of the next player and the list of chess pieces of that color from the repository.
     * It then retrieves the king of the next player from the repository.
     * The method then checks if any of the chess pieces of the next player can make a legal move.
     * If no piece can make a legal move, the game is in stalemate and the method updates the move report accordingly.
     * If the game is in stalemate and the king of the next player is in check, the game is in checkmate and the method
     * updates the move report accordingly.
     */
    private void checkStalemateOrCheckMate() {
        ChessPieceColor color = moveReport.getNextColor();
        List<ChessPiece> chessPieces = repository.getChessPieces(color);
        ChessPiece king = repository.getKing(chessPieces, color).orElseThrow();
        boolean isStalemate = true;
        for (ChessPiece chessPiece : chessPieces) {
            if (canMoveAnyWhere(chessPiece)) {
                isStalemate = false;
                break;
            }
        }
        if (isStalemate) {
            moveReport.setStalemate();
        }
        if (isStalemate && isCheck(king)) {
            moveReport.setCheckMate();
        }
    }

    /**
     * This method checks if the given chess piece can move to any position on the chess board.
     * It iterates over all the squares on the board, creating a MoveDto object for each square with the given chess piece.
     * It then checks if the chess piece can move to the square using the MoveValidator's canMove method.
     * If the chess piece can move to a square, the method returns true.
     * If the chess piece cannot move to any square, the method returns false.
     *
     * @param chessPiece The chess piece to check for possible moves.
     * @return True if the chess piece can move to any square on the board, false otherwise.
     */
    private boolean canMoveAnyWhere(ChessPiece chessPiece) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                MoveDto moveDto = MoveDto.create(chessPiece, i, j);
                if (moveValidator.canMove(moveDto)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks if the given chess piece (king) is under attack.
     * It uses the MoveValidator's method noAttacksFromEnemies to check if there are no attacks from enemy pieces.
     * If there are no attacks, the method returns false, indicating that the king is not in check.
     * If there are attacks, the method returns true, indicating that the king is in check.
     *
     * @param king The chess piece (king) to check for attacks.
     * @return True if the king is in check, false otherwise.
     */
    private boolean isCheck(ChessPiece king) {
        return !moveValidator.noAttacksFromEnemies(king);
    }

    /**
     * This method is responsible for capturing a chess piece on the board.
     * It iterates over all the chess pieces in the repository and checks if any of them occupy the same position as the
     * target of the current move.
     * If a piece is found and it is not of the same color as the piece in the current move, it is removed from the repository
     * and its reference is stored in the move report.
     * The removal of the piece from the repository is done using the iterator's remove method to avoid ConcurrentModificationException.
     */
    private void capturePiece() {
        Iterator<ChessPiece> iterator = repository.getChessPieces().iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == move.x() && chessPieceToRemove.getY() == move.y() &&
                !moveReport.getChessPieceInUse().hasSameColor(chessPieceToRemove)) {
                moveReport.setChessPieceToRemove(chessPieceToRemove);
                iterator.remove();
            }
        }
    }

    /**
     * This method is responsible for moving the rook before the king during a castling move in chess.
     * Castling is a special move in chess involving the king and one of the rooks of the same color.
     * The king moves two squares towards the rook, and the rook moves to the square the king skipped over.
     * This method checks if the current move is a castling move, and if so, retrieves the rook to be moved.
     * If the rook is present, it calls the method to move the rook.
     */
    private void moveRookBeforeKing() {
        if (isNotKingOrNotCastling()) {
            return;
        }
        Optional<Rook> rookToMove = repository.getRookByKingMove(move.x(), move.inUse().getY());
        if (rookToMove.isPresent()) {
            Rook rook = rookToMove.get();
            moveRook(rook);
        }
    }

    /**
     * This method checks if the current move is not a castling move.
     * Castling is a special move in chess involving the king and one of the rooks of the same color.
     * The king moves two squares towards the rook, and the rook moves to the square the king skipped over.
     * This method checks if the piece in the current move is not a king or if the move is not a two-square move,
     * which are the conditions for a castling move.
     *
     * @return True if the current move is not a castling move, false otherwise.
     */
    private boolean isNotKingOrNotCastling() {
        boolean isNotKingOrNotCastling = !move.inUse().hasType(ChessPieceType.KING);
        if (Math.abs(move.inUse().getX() - move.x()) != 2) {
            isNotKingOrNotCastling = true;
        }
        return isNotKingOrNotCastling;
    }

    /**
     * This method is responsible for moving the rook during a castling move in chess.
     * Castling is a special move in chess involving the king and one of the rooks of the same color.
     * The rook moves to the square the king skipped over, while the king is moved two squares towards the rook.
     * This method updates the position of the rook and records the move in the move report.
     *
     * @param rookToMove The rook that is to be moved during castling.
     */
    private void moveRook(Rook rookToMove) {
        int newXRook = rookToMove.getCastlingX();
        moveReport.setRookToMove(rookToMove);
        rookToMove.move(newXRook, rookToMove.getY());
    }

    /**
     * This method handles the promotion of a pawn in a chess game.
     * In chess, a pawn that reaches the opposite end of the board (rank 8 for white, rank 1 for black)
     * can be promoted to any other piece (except king).
     * This method checks if the piece in the current move is a pawn and if it has reached the end of the board.
     * If both conditions are met, it sets the pawn to be removed in the move report and calls the method to change the pawn
     * to another piece.
     */
    private void pawnPromotion() {
        if (!move.inUse().hasType(ChessPieceType.PAWN)) {
            return;
        }
        if (move.y() == 0 || move.y() == 7) {
            moveReport.setPromotionPawnToRemove();
            changePawnToFigure();
        }
    }

    /**
     * This method is responsible for promoting a pawn to a different chess piece.
     * The promotion occurs when a pawn reaches the opposite end of the chessboard.
     * The type of piece the pawn is promoted to is determined by the `createPromotionFigure` method of the `chessPieceFactory`.
     * The original pawn is removed from the repository and the new piece is added.
     * The `moveReport` is updated with the new piece as the target of the promotion.
     */
    private void changePawnToFigure() {
        ChessPiece newFigure = chessPieceFactory.createPromotionFigure(move);
        repository.remove(move.inUse());
        repository.add(newFigure);
        moveReport.setPromotionTarget(newFigure);
    }

    /**
     * Checks if a promotion is possible for a given move.
     *
     * @param moveDto The move to be checked.
     * @return True if a promotion is possible, false otherwise.
     */
    boolean isPromotion(MoveDto moveDto) {
        return moveDto.inUse().hasType(ChessPieceType.PAWN) && (moveDto.y() == 0 || moveDto.y() == 7) &&
            moveValidator.canMove(moveDto);
    }
}
