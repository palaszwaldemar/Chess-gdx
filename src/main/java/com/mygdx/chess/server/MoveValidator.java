package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for validating the moves in a chess game.
 */
class MoveValidator {
    private final ChessPieceRepository repository;
    private MoveDto move;

    /**
     * Constructor for the MoveValidator class.
     *
     * @param repository The repository of chess pieces.
     */
    MoveValidator(ChessPieceRepository repository) {
        this.repository = repository;
    }

    /**
     * Checks if a move is valid.
     *
     * @param move The move to be validated.
     * @return true if the move is valid, false otherwise.
     */
    boolean canMove(MoveDto move) {
        this.move = move;
        return isOnTheBoard() && friendIsNotHere() && move.inUse().isCorrectMovement(move.x(), move.y()) &&
            kingWillNotBeInCheck() && isValidMoveByChessPieceType();
    }

    /**
     * Checks if the move is within the board.
     *
     * @return true if the move is within the board, false otherwise.
     */
    private boolean isOnTheBoard() {
        return move.x() >= 0 && move.x() <= 7 && move.y() >= 0 && move.y() <= 7;
    }

    /**
     * Checks if there is a friendly piece on the destination square.
     *
     * @return true if there is no friendly piece on the destination square, false otherwise.
     */
    private boolean friendIsNotHere() {
        List<ChessPiece> chessPieces = repository.getChessPieces(move.inUse().getColor());
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == move.x() && chessPiece.getY() == move.y()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the move is valid according to the type of the chess piece.
     *
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMoveByChessPieceType() {
        return switch (move.inUse().getType()) {
            case PAWN -> isValidPawnMove();
            case ROOK, RUNNER, QUEEN -> isClearLineForChessPieceInUse();
            case KING -> {
                ChessPiece king = move.inUse();
                yield isValidKingMove(king);
            }
            default -> true;
        };
    }

    /**
     * Checks if the king will be in check after the move.
     *
     * @return true if the king will not be in check, false otherwise.
     */
    private boolean kingWillNotBeInCheck() {
        List<ChessPiece> chessPiecesAfterMove = repository.getChessPiecesAfterMove(move.inUse(), move.x(), move.y());
        List<ChessPiece> enemyChessPiecesAfterMove =
            getEnemiesAfterMove(move.inUse().getEnemyColor(), chessPiecesAfterMove);
        ChessPiece friendKing = repository.getKing(chessPiecesAfterMove, move.inUse().getColor()).orElseThrow();
        int kingX = friendKing.getX();
        int kingY = friendKing.getY();
        for (ChessPiece chessPiece : enemyChessPiecesAfterMove) {
            if (isCheck(chessPiece, kingX, kingY, chessPiecesAfterMove)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the enemy pieces after a move.
     *
     * @param enemyColor           The color of the enemy pieces.
     * @param chessPiecesAfterMove The list of chess pieces after the move.
     * @return A list of enemy pieces after the move.
     */
    private List<ChessPiece> getEnemiesAfterMove(ChessPieceColor enemyColor, List<ChessPiece> chessPiecesAfterMove) {
        List<ChessPiece> enemiesAfterMove = new ArrayList<>();
        for (ChessPiece chessPiece : chessPiecesAfterMove) {
            if (chessPiece.hasColor(enemyColor)) {
                enemiesAfterMove.add(chessPiece);
            }
        }
        return enemiesAfterMove;
    }

    /**
     * Checks if a piece is attacking the king.
     *
     * @param defendingChessPiece The piece that is defending the king.
     * @param kingX               The x-coordinate of the king.
     * @param kingY               The y-coordinate of the king.
     * @param chessPieces         The list of chess pieces.
     * @return true if the piece is attacking the king, false otherwise.
     */
    private boolean isCheck(ChessPiece defendingChessPiece, int kingX, int kingY, List<ChessPiece> chessPieces) {
        return switch (defendingChessPiece.getType()) {
            case PAWN -> {
                Pawn pawn = (Pawn) defendingChessPiece;
                yield pawn.isAttackingField(kingX, kingY);
            }
            case KNIGHT -> defendingChessPiece.isCorrectMovement(kingX, kingY);
            case ROOK, RUNNER, QUEEN -> isClearLineForChessPiece(defendingChessPiece, kingX, kingY, chessPieces) &&
                defendingChessPiece.isCorrectMovement(kingX, kingY);
            case KING -> kingDefendingFiled(defendingChessPiece, kingX, kingY);
        };
    }

    /**
     * Checks if the king is defending a field.
     *
     * @param enemyKing The enemy king.
     * @param kingX     The x-coordinate of the king.
     * @param kingY     The y-coordinate of the king.
     * @return true if the king is defending the field, false otherwise.
     */
    private boolean kingDefendingFiled(ChessPiece enemyKing, int kingX, int kingY) {
        int deltaX = Math.abs(enemyKing.getX() - kingX);
        int deltaY = Math.abs(enemyKing.getY() - kingY);
        return (deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1);
    }

    /**
     * Checks if a pawn move is valid.
     *
     * @return true if the pawn move is valid, false otherwise.
     */
    private boolean isValidPawnMove() {
        List<ChessPiece> chessPieces = repository.getChessPieces();
        int deltaX = Math.abs(move.x() - move.inUse().getX());
        int deltaY = Math.abs(move.y() - move.inUse().getY());
        boolean diagonalMove = deltaX == deltaY;
        if (diagonalMove) {
            return !isFreeField(move.x(), move.y(), chessPieces);
        } else {
            return isClearLineForChessPieceInUse() && isFreeField(move.x(), move.y(), chessPieces);
        }
    }

    /**
     * Checks if a field is free.
     *
     * @param x           The x-coordinate of the field.
     * @param y           The y-coordinate of the field.
     * @param chessPieces The list of chess pieces.
     * @return true if the field is free, false otherwise.
     */
    private boolean isFreeField(int x, int y, List<ChessPiece> chessPieces) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there is a clear line for the chess piece in use.
     *
     * @return true if there is a clear line, false otherwise.
     */
    private boolean isClearLineForChessPieceInUse() {
        return isClearLineForChessPiece(move.inUse(), move.x(), move.y(), repository.getChessPieces());
    }

    /**
     * Checks if there is a clear line for a chess piece.
     *
     * @param chessPiece  The chess piece.
     * @param x           The x-coordinate of the destination square.
     * @param y           The y-coordinate of the destination square.
     * @param chessPieces The list of chess pieces.
     * @return true if there is a clear line, false otherwise.
     */
    private boolean isClearLineForChessPiece(ChessPiece chessPiece, int x, int y, List<ChessPiece> chessPieces) {
        int startX = chessPiece.getX();
        int startY = chessPiece.getY();
        int deltaX = x - startX;
        int deltaY = y - startY;
        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int nextX = startX + i * Integer.signum(deltaX);
            int nextY = startY + i * Integer.signum(deltaY);
            if (!isFreeField(nextX, nextY, chessPieces)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a king move is valid.
     *
     * @param king The king.
     * @return true if the king move is valid, false otherwise.
     */
    private boolean isValidKingMove(ChessPiece king) {
        return !isCastling() || isValidCastling(king);
    }

    /**
     * Checks if a castling move is valid.
     *
     * @param king The king.
     * @return true if the castling move is valid, false otherwise.
     */
    private boolean isValidCastling(ChessPiece king) {
        Optional<Rook> optionalRook = repository.getRookByKingMove(move.x(), move.inUse().getY());
        if (optionalRook.isPresent()) {
            Rook rook = optionalRook.get();
            return rook.wasNotMoved() && move.inUse().wasNotMoved() && isClearCastlingLine() &&
                noAttacksFromEnemies(king);
        }
        return false;
    }

    /**
     * Checks if a move is a castling move.
     *
     * @return true if the move is a castling move, false otherwise.
     */
    private boolean isCastling() {
        return Math.abs(move.inUse().getX() - move.x()) == 2;
    }

    /**
     * Checks if there is a clear line for a castling move.
     *
     * @return true if there is a clear line, false otherwise.
     */
    private boolean isClearCastlingLine() {
        ChessPieceColor enemyColor = move.inUse().getEnemyColor();
        List<ChessPiece> chessPieces = repository.getChessPieces();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int startXLineToRightSide = move.x() == 6 ? 5 : 1;
        int endXLineFromLeftSide = move.x() == 6 ? 7 : 4;
        int y = move.inUse().getY();
        for (int i = startXLineToRightSide; i < endXLineFromLeftSide; i++) {
            for (ChessPiece enemyChessPiece : enemyChessPieces) {
                MoveDto moveDto = MoveDto.create(enemyChessPiece, i, y);
                if (!isFreeField(i, y, chessPieces) || canMove(moveDto)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there are no attacks from enemy pieces.
     *
     * @param target The target piece.
     * @return true if there are no attacks, false otherwise.
     */
    boolean noAttacksFromEnemies(ChessPiece target) {
        int x = target.getX();
        int y = target.getY();
        ChessPieceColor enemyColor = target.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            MoveDto moveDto = MoveDto.create(enemyChessPiece, x, y);
            if (canMove(moveDto)) {
                return false;
            }
        }
        return true;
    }
}