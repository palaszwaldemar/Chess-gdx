package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Pawn;
import com.mygdx.chess.server.chessPieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoveValidator {
    private final ChessPieceRepository repository;
    private int x;
    private int y;
    private ChessPiece chessPieceInUse;

    public MoveValidator(ChessPieceRepository repository) {
        this.repository = repository;
    }

    boolean canMove(ChessPiece chessPieceInUse, int x, int y) {
        this.x = x;
        this.y = y;
        this.chessPieceInUse = chessPieceInUse;
        return isOnTheBoard() && friendIsNotHere() && chessPieceInUse.isCorrectMovement(x, y) &&
            kingWillNotBeInCheck() && isValidMoveByChessPieceType();
    }

    private boolean isOnTheBoard() {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean friendIsNotHere() {
        List<ChessPiece> chessPieces = repository.getChessPieces(chessPieceInUse.getColor());
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMoveByChessPieceType() {
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isValidPawnMove();
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLineForChessPieceInUse();
            case KING:
                return isValidKingMove();
        }
        return true;
    }

    private boolean kingWillNotBeInCheck() {
        List<ChessPiece> chessPiecesAfterMove = repository.getChessPiecesAfterMove(chessPieceInUse, x, y);
        List<ChessPiece> enemyChessPiecesAfterMove =
            getEnemiesAfterMove(chessPieceInUse.getEnemyColor(), chessPiecesAfterMove);
        ChessPiece friendKing = repository.getKing(chessPiecesAfterMove, chessPieceInUse.getColor()).orElseThrow();
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
        switch (defendingChessPiece.getType()) {
            case PAWN:
                Pawn pawn = (Pawn) defendingChessPiece;
                return pawn.isAttackingField(kingX, kingY);
            case KNIGHT:
                return defendingChessPiece.isCorrectMovement(kingX, kingY);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLineForChessPiece(defendingChessPiece, kingX, kingY, chessPieces) &&
                    defendingChessPiece.isCorrectMovement(kingX, kingY);
            case KING:
                return kingDefendingFiled(defendingChessPiece, kingX, kingY);
        }
        return false;
    }

    private boolean kingDefendingFiled(ChessPiece enemyKing, int kingX, int kingY) {
        int deltaX = Math.abs(enemyKing.getX() - kingX);
        int deltaY = Math.abs(enemyKing.getY() - kingY);
        return (deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1);
    }

    private boolean isValidPawnMove() {
        List<ChessPiece> chessPieces = repository.getChessPieces();
        int deltaX = Math.abs(x - chessPieceInUse.getX());
        int deltaY = Math.abs(y - chessPieceInUse.getY());
        boolean diagonalMove = deltaX == deltaY;
        if (diagonalMove) {
            return !isFreeField(x, y, chessPieces);
        } else {
            return isClearLineForChessPieceInUse() && isFreeField(x, y, chessPieces);
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
        return isClearLineForChessPiece(chessPieceInUse, x, y, repository.getChessPieces());
    }

    private boolean isClearLineForChessPiece(ChessPiece chessPiece, int x , int y, List<ChessPiece> chessPieces) {
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

    private boolean isValidKingMove() { // CHECK : 18.01.2024 na pewno dobrze napisane?
        return !isCastling() || isValidCastling();
    }

    private boolean isValidCastling() {
        Optional<Rook> optionalRook = repository.getRookByKingMove(x, chessPieceInUse.getY());
        if (optionalRook.isPresent()) {
            Rook rook = optionalRook.get();
            return rook.wasNotMoved() && chessPieceInUse.wasNotMoved() && isClearCastlingLine() &&
                noAttacksFromEnemies();
        }
        return false;
    }

    private boolean isCastling() {
        return Math.abs(chessPieceInUse.getX() - x) == 2;
    }

    private boolean isClearCastlingLine() {
        ChessPieceColor enemyColor = chessPieceInUse.getEnemyColor();
        List<ChessPiece> chessPieces = repository.getChessPieces();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int startXLineToRightSide = x == 6 ? 5 : 1;
        int endXLineFromLeftSide = x == 6 ? 7 : 4;
        int y = chessPieceInUse.getY();
        for (int i = startXLineToRightSide; i < endXLineFromLeftSide; i++) {
            for (ChessPiece enemyChessPiece : enemyChessPieces) {
                if (!isFreeField(i, y, chessPieces) || canMove(enemyChessPiece, i, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean noAttacksFromEnemies() {
        int x = chessPieceInUse.getX();
        int y = chessPieceInUse.getY();
        ChessPieceColor enemyColor = chessPieceInUse.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (canMove(enemyChessPiece, x, y)) {
                return false;
            }
        }
        return true;
    }
}