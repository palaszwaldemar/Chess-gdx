package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Pawn;
import com.mygdx.chess.server.chessPieces.Rook;

import java.util.List;
import java.util.Optional;

public class MoveValidator {
    private final ChessPieceRepository repository;

    public MoveValidator(ChessPieceRepository repository) {
        this.repository = repository;
    }

    boolean canMove(ChessPiece chessPieceInUse, int x, int y) {
        ChessPieceColor color = chessPieceInUse.getColor();
        return isOnTheBoard(x, y) && friendIsNotHere(color, x, y) &&
            chessPieceInUse.isCorrectMovement(x, y) &&
            isValidMoveForChessPiece(chessPieceInUse, x, y);
    }

    private boolean isOnTheBoard(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean friendIsNotHere(ChessPieceColor color, int x, int y) {
        List<ChessPiece> chessPieces = repository.getChessPieces(color);
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
                return isValidCastling(chessPieceInUse, x) || isValidNormalMove(chessPieceInUse, x, y);
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

    private boolean isValidCastling(ChessPiece king, int x) {
        Optional<Rook> optionalRook = repository.getRookByKingMove(x, king.getY());
        if (optionalRook.isPresent()) {
            Rook rook = optionalRook.get();
            return !rook.wasMoved() && isClearCastlingLine(king, x) && noAttacksFromEnemies(king);
        }
        return false;
    }

    private boolean isValidNormalMove(ChessPiece king, int x, int y) {
        if (isCastling(king, x)) {
            return false;
        }
        return true;
    }

    private boolean isValidCastlingOrNormalMove(ChessPiece king, int x, int y) {
        if (kingWillBeInCheck(king.getEnemyColor(), x, y)) {
            return false;
        }
        if (!isCastling(king, x)) {
            return true;
        }
        return isValidCastling(king, x);
    }

    private boolean kingWillBeInCheck(ChessPieceColor enemyColor, int x, int y) {
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (isNotOnAttackingField(enemyChessPiece, x, y) &&
                fieldIsDefended(enemyChessPiece, x, y)) {
                System.out.println("FIGURA BRONI TEGO POLA");
                System.out.println(
                    "1. FIGURA: " + enemyChessPiece.getType() + " " + enemyChessPiece.getColor());
                System.out.println(
                    "2. WSPÓŁRZĘDNE BRONIĄCEJ FIGURY: x = " + enemyChessPiece.getX() + ", y = " +
                        enemyChessPiece.getY());
                System.out.println("-----\n\n");
                return true;
            }
        }
        return false;
        // TODO: 11.01.2024 szachowany król może ruszyć na szachowane pole przez tą samą figurę,
        // todo jeżeli to pole będzie dalej od figury
        // TODO: 11.01.2024 jeżeli przeciwny król nie zrobił żadnego ruchu to w linii X nie można się do
        // todo niego zbliżyć na odległość dwóch pól
    }

    private boolean isNotOnAttackingField(ChessPiece enemyChessPiece, int x, int y) {
        return enemyChessPiece.getX() != x || enemyChessPiece.getY() != y;
    }

    private boolean fieldIsDefended(ChessPiece defendingChessPiece, int x, int y) {
        switch (defendingChessPiece.getType()) {
            case PAWN:
                Pawn pawn = (Pawn) defendingChessPiece;
                return pawn.isAttackingField(x, y);
            case KNIGHT:
                return defendingChessPiece.isCorrectMovement(x, y);
            case ROOK:
            case RUNNER:
            case QUEEN:
            case KING:
                return defendingChessPiece.isCorrectMovement(x, y) &&
                    isClearLine(defendingChessPiece, x, y);
        }
        return false;
    }

    private boolean isCastling(ChessPiece king, int x) {
        return Math.abs(king.getX() - x) == 2;
    }

    private boolean isClearCastlingLine(ChessPiece king, int x) {
        ChessPieceColor enemyColor = king.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int startXLineToRightSide = x == 6 ? 5 : 1;
        int endXLineFromLeftSide = x == 6 ? 7 : 4;
        int y = king.getY();
        for (int i = startXLineToRightSide; i < endXLineFromLeftSide; i++) {
            for (ChessPiece enemyChessPiece : enemyChessPieces) {
                if (!isFreeField(i, y) || canMove(enemyChessPiece, i, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean noAttacksFromEnemies(ChessPiece captureChessPiece) {
        int x = captureChessPiece.getX();
        int y = captureChessPiece.getY();
        ChessPieceColor enemyColor = captureChessPiece.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (canMove(enemyChessPiece, x, y)) {
                return false;
            }
        }
        return true;
    }
}
