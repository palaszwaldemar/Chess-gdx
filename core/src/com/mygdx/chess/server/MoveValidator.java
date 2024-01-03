package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class MoveValidator {
    private final ChessPieceRepository repository;

    public MoveValidator(ChessPieceRepository repository) {
        this.repository = repository;
    }

    boolean canMove(ChessPiece chessPieceInUse, int x, int y) {
        ChessPieceColor color = chessPieceInUse.getColor();
        return isOnTheBoard(x, y) && friendIsNotHere(color, x, y) && chessPieceInUse.isCorrectMovement(x, y) &&
            isValidMoveForChessPiece(chessPieceInUse, x, y);
    }

    private boolean isOnTheBoard(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean friendIsNotHere(ChessPieceColor color, int x, int y) {
        List<ChessPiece> chessPieces = repository.getChessPiecesByColor(color);
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMoveForChessPiece(ChessPiece chessPieceInUse, int x, int y) {
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isCorrectPawnMove(chessPieceInUse, x, y);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLine(chessPieceInUse, x, y);
            case KING:
                return isValidCastlingOrNormalMove(chessPieceInUse, x);
        }
        return true;
    }

    private boolean isCorrectPawnMove(ChessPiece chessPieceInUse, int x, int y) {
        int deltaX = Math.abs(x - chessPieceInUse.getX());
        int deltaY = Math.abs(y - chessPieceInUse.getY());
        boolean diagonalMove = deltaX == deltaY;
        if (diagonalMove) {
            return !isFreeField(x, y);
        } else {
            return isClearLine(chessPieceInUse, x, y) && isFreeField(x, y);
        }
    }

    private boolean isFreeField(int x, int y) {
        List<ChessPiece> chessPieces = repository.getChessPieces();
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isClearLine(ChessPiece chessPieceInUse, int x, int y) {
        int startX = chessPieceInUse.getX();
        int startY = chessPieceInUse.getY();
        int deltaX = x - startX;
        int deltaY = y - startY;
        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int nextX = startX + i * Integer.signum(deltaX);
            int nextY = startY + i * Integer.signum(deltaY);
            if (!isFreeField(nextX, nextY)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCastlingOrNormalMove(ChessPiece king, int x) {
        if (!isCastling(king, x)) {
            return true;
        }
        if (enemyAttackingChessPiece(king)) {
            return false;
        }
        int xRook = isRightCastling(king, x) ? 7 : 0;
        return isValidCastling(king, xRook);
    }

    private boolean isCastling(ChessPiece king, int x) {
        return Math.abs(king.getX() - x) == 2;
    }

    private boolean enemyAttackingChessPiece(ChessPiece captureChessPiece) {
        int x = captureChessPiece.getX();
        int y = captureChessPiece.getY();
        ChessPieceColor enemyColor = captureChessPiece.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPiecesByColor(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (canMove(enemyChessPiece, x, y)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightCastling(ChessPiece king, int x) {
        return king.getX() < x;
    }

    private boolean isValidCastling(ChessPiece king, int xRook) {
        return isValidCastlingByRook(king, xRook) && isClearCastlingLine(king, xRook);
    }

    private boolean isValidCastlingByRook(ChessPiece king, int xRook) {
        List<ChessPiece> rooks = repository.getRooksByColor(king.getColor());
        for (ChessPiece rook : rooks) {
            if (rook.getX() == xRook && !rook.wasMoved()) {
                return true;
            }
        }
        return false;
    }

    private boolean isClearCastlingLine(ChessPiece king, int xRook) {
        ChessPieceColor enemyColor = king.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPiecesByColor(enemyColor);
        int startXLineToRightSide = xRook == 7 ? 5 : 1; // CHECK : 12.12.2023 czy teraz nazwa zmiennej zrozumiała?
        int endXLineFromLeftSide = xRook == 7 ? 7 : 4; // CHECK : 12.12.2023 czy teraz nazwa zmiennej zrozumiała?
        int y = king.getY();
        for (int i = startXLineToRightSide; i < endXLineFromLeftSide; i++) {
            for (ChessPiece enemyChessPiece : enemyChessPieces) {
                if (!isFreeField(i, y) || enemyAttackingField(enemyChessPiece, i, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean enemyAttackingField(ChessPiece enemyChessPiece, int x, int y) {
        return canMove(enemyChessPiece, x, y);
    }
}
