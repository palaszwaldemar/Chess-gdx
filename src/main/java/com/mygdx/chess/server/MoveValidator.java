package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class MoveValidator {
    private final ChessPieceRepository repository;
    private MoveDto move;

    MoveValidator(ChessPieceRepository repository) {
        this.repository = repository;
    }

    boolean canMove(MoveDto move) {
        this.move = move;
        return isOnTheBoard() && friendIsNotHere() && move.inUse().isCorrectMovement(move.x(), move.y()) &&
            kingWillNotBeInCheck() && isValidMoveByChessPieceType();
    }

    private boolean isOnTheBoard() {
        return move.x() >= 0 && move.x() <= 7 && move.y() >= 0 && move.y() <= 7;
    }

    private boolean friendIsNotHere() {
        List<ChessPiece> chessPieces = repository.getChessPieces(move.inUse().getColor());
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == move.x() && chessPiece.getY() == move.y()) {
                return false;
            }
        }
        return true;
    }

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

    private List<ChessPiece> getEnemiesAfterMove(ChessPieceColor enemyColor, List<ChessPiece> chessPiecesAfterMove) {
        List<ChessPiece> enemiesAfterMove = new ArrayList<>();
        for (ChessPiece chessPiece : chessPiecesAfterMove) {
            if (chessPiece.hasColor(enemyColor)) {
                enemiesAfterMove.add(chessPiece);
            }
        }
        return enemiesAfterMove;
    }

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

    private boolean kingDefendingFiled(ChessPiece enemyKing, int kingX, int kingY) {
        int deltaX = Math.abs(enemyKing.getX() - kingX);
        int deltaY = Math.abs(enemyKing.getY() - kingY);
        return (deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1);
    }

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

    private boolean isFreeField(int x, int y, List<ChessPiece> chessPieces) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isClearLineForChessPieceInUse() {
        return isClearLineForChessPiece(move.inUse(), move.x(), move.y(), repository.getChessPieces());
    }

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

    private boolean isValidKingMove(ChessPiece king) {
        return !isCastling() || isValidCastling(king);
    }

    private boolean isValidCastling(ChessPiece king) {
        Optional<Rook> optionalRook = repository.getRookByKingMove(move.x(), move.inUse().getY());
        if (optionalRook.isPresent()) {
            Rook rook = optionalRook.get();
            return rook.wasNotMoved() && move.inUse().wasNotMoved() && isClearCastlingLine() &&
                noAttacksFromEnemies(king);
        }
        return false;
    }

    private boolean isCastling() {
        return Math.abs(move.inUse().getX() - move.x()) == 2;
    }

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