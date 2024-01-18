package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Pawn;
import com.mygdx.chess.server.chessPieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<ChessPiece> chessPieces = repository.getChessPieces(color);
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMoveForChessPiece(ChessPiece chessPieceInUse, int x, int y) {
        if (kingWillBeInCheck(chessPieceInUse, x, y)) {
            return false;
        }
        List<ChessPiece> chessPieces = repository.getChessPieces();
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isValidPawnMove(chessPieceInUse, x, y, chessPieces);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLine(chessPieceInUse, x, y, chessPieces);
            case KING:
                return isValidKingMove(chessPieceInUse, x);
        }
        return true;
    }

    private boolean kingWillBeInCheck(ChessPiece chessPieceInUse, int x, int y) {
        List<ChessPiece> chessPiecesAfterMove = repository.getChessPiecesAfterMove(chessPieceInUse, x, y);
        List<ChessPiece> enemyChessPiecesAfterMove =
            getEnemiesAfterMove(chessPieceInUse.getEnemyColor(), chessPiecesAfterMove);
        ChessPiece friendKing = repository.getKing(chessPiecesAfterMove, chessPieceInUse.getColor()).orElseThrow();
        int kingX = friendKing.getX();
        int kingY = friendKing.getY();
        for (ChessPiece chessPiece : enemyChessPiecesAfterMove) {
            if (isCheck(chessPiece, kingX, kingY, chessPiecesAfterMove)) {
                return true;
            }
        }
        return false;
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

    private boolean isCheck(ChessPiece defendingChessPiece, int x, int y, List<ChessPiece> chessPieces) {
        switch (defendingChessPiece.getType()) {
            case PAWN:
                Pawn pawn = (Pawn) defendingChessPiece;
                return pawn.isAttackingField(x, y);
            case KNIGHT:
                return defendingChessPiece.isCorrectMovement(x, y);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLine(defendingChessPiece, x, y, chessPieces) &&
                    defendingChessPiece.isCorrectMovement(x, y);
            case KING:
                return kingDefendingFiled(defendingChessPiece, x, y);
        }
        return false;
    }

    private boolean kingDefendingFiled(ChessPiece enemyKing, int x, int y) {
        int deltaX = Math.abs(enemyKing.getX() - x);
        int deltaY = Math.abs(enemyKing.getY() - y);
        return (deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1);
    }

    private boolean isValidPawnMove(ChessPiece pawn, int x, int y, List<ChessPiece> chessPieces) {
        int deltaX = Math.abs(x - pawn.getX());
        int deltaY = Math.abs(y - pawn.getY());
        boolean diagonalMove = deltaX == deltaY;
        if (diagonalMove) {
            return !isFreeField(x, y, chessPieces);
        } else {
            return isClearLine(pawn, x, y, chessPieces) && isFreeField(x, y, chessPieces);
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

    private boolean isClearLine(ChessPiece chessPieceInUse, int x, int y, List<ChessPiece> chessPieces) {
        int startX = chessPieceInUse.getX();
        int startY = chessPieceInUse.getY();
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

    private boolean isValidKingMove(ChessPiece king, int x) {
        return isValidCastling(king, x) || isValidNormalMove(king, x);
    }

    private boolean isValidCastling(ChessPiece king, int x) {
        Optional<Rook> optionalRook = repository.getRookByKingMove(x, king.getY());
        if (optionalRook.isPresent()) {
            Rook rook = optionalRook.get();
            return rook.wasNotMoved() && king.wasNotMoved() && isClearCastlingLine(king, x) &&
                noAttacksFromEnemies(king);
        }
        return false;
    }

    private boolean isValidNormalMove(ChessPiece king, int x) {
        return !isCastling(king, x);
    }

    private boolean isCastling(ChessPiece king, int x) {
        return Math.abs(king.getX() - x) == 2;
    }

    private boolean isClearCastlingLine(ChessPiece king, int x) {
        ChessPieceColor enemyColor = king.getEnemyColor();
        List<ChessPiece> chessPieces = repository.getChessPieces();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int startXLineToRightSide = x == 6 ? 5 : 1;
        int endXLineFromLeftSide = x == 6 ? 7 : 4;
        int y = king.getY();
        for (int i = startXLineToRightSide; i < endXLineFromLeftSide; i++) {
            for (ChessPiece enemyChessPiece : enemyChessPieces) {
                if (!isFreeField(i, y, chessPieces) || canMove(enemyChessPiece, i, y)) {
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