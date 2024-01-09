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
                return isValidCastlingOrNormalMove(chessPieceInUse, x, y);
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

    private boolean isValidCastlingOrNormalMove(ChessPiece king, int x, int y) {
        if (kingWillBeInCheck(king, x, y)) {
            return false;
        }
        if (!isCastling(king, x)) {
            return true;
        }
        if (enemyAttackingChessPiece(king)) {
            return false;
        }
        return isValidCastling(king, x);
    }

    private boolean kingWillBeInCheck(ChessPiece king, int x, int y) {// TODO: 09.01.2024 popracować nad czytelnością metody
        ChessPieceColor enemyColor = king.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (enemyChessPiece.getType() == ChessPieceType.PAWN) {
                Pawn pawn = (Pawn) enemyChessPiece;
                if (pawn.attackField(x, y)) {
                    System.out.println("pion atakuje to pole");
                    return true;
                }
            }
            if (enemyChessPiece.getType() != ChessPieceType.PAWN &&
                canMove(enemyChessPiece, x, y)) {
                System.out.println("figura atakuje to pole typ:" + enemyChessPiece.getType());
                return true;
            }
            if (enemyChessPiece.getType() != ChessPieceType.PAWN &&
                (enemyChessPiece.getX() != x || enemyChessPiece.getY() != y) &&
                isClearLine(enemyChessPiece, x, y) && enemyChessPiece.isCorrectMovement(x, y)) {
                System.out.println("po zbiciu tej figury król będzie pod szachem");
                System.out.println("będzie szachowała figura: typ:" + enemyChessPiece.getType());
                System.out.println("kolor: " + enemyChessPiece.getColor());
                System.out.println("wspolrzedne: x = " + enemyChessPiece.getX() + ", y = " +
                    enemyChessPiece.getY());
                System.out.println();
                System.out.println("-----");
                return true;
            }
        }
        return false;
    }

    private boolean isCastling(ChessPiece king, int x) {
        return Math.abs(king.getX() - x) == 2;
    }

    private boolean enemyAttackingChessPiece(ChessPiece captureChessPiece) {// CHECK : 09.01.2024 druga metoda bardzo podobna. Usunąć jedną z nich?
        int x = captureChessPiece.getX();
        int y = captureChessPiece.getY();
        ChessPieceColor enemyColor = captureChessPiece.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (canMove(enemyChessPiece, x, y)) {
                return true;
            }
        }
        return false;
    }
    
    /*private boolean isValidCastling(ChessPiece king, int x) {
        Rook rook = repository.getRookByKingMove(x, king.getY()).orElseThrow();
        return !rook.wasMoved() && isClearCastlingLine(king, x);
    }*/

    private boolean isValidCastling(ChessPiece king, int x) { // CHECK : 07.01.2024 czy może być tak zamiast poprzedniej metody?
        Optional<Rook> optionalRook = repository.getRookByKingMove(x, king.getY());
        if (optionalRook.isPresent()) {
            Rook rook = optionalRook.get();
            return !rook.wasMoved() && isClearCastlingLine(king, x);
        }
        return false;
    }

    private boolean isClearCastlingLine(ChessPiece king, int x) {// CHECK : 09.01.2024 czy metoda dobrze napisana?
        ChessPieceColor enemyColor = king.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int startXLineToRightSide = x == 6 ? 5 : 1;
        int endXLineFromLeftSide = x == 6 ? 7 : 4;
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
// CHECK : 09.01.2024 król nie może pójść na pole, jeżeli to pole jest szachowane. Problem z pionem. Jak go rozwiązać?
// CHECK : 09.01.2024 problem z pójściem króla na pole obok innego króla
