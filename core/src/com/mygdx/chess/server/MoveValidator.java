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
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isValidPawnMove(chessPieceInUse, x, y) && kingWillNotInCheck(chessPieceInUse, x, y);
            case KNIGHT:
                return kingWillNotInCheck(chessPieceInUse, x, y);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLine(chessPieceInUse, x, y) && kingWillNotInCheck(chessPieceInUse, x, y);
            case KING:
                return isValidKingMove(chessPieceInUse, x, y);
        }
        return true;
    }

    private boolean kingWillNotInCheck(ChessPiece chessPieceInUse, int x, int y) {
        ChessPieceColor enemyColor = chessPieceInUse.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int actualChessPieceX = chessPieceInUse.getX();
        int actualChessPieceY = chessPieceInUse.getY();
        Optional<ChessPiece> friendKingOptional = repository.getKing(chessPieceInUse.getColor());
        if (friendKingOptional.isPresent()) {
            ChessPiece friendKing = friendKingOptional.get();
            chessPieceInUse.move(x, y);
            for (ChessPiece enemyChessPiece : enemyChessPieces) {
                if (canMove(enemyChessPiece, friendKing.getX(), friendKing.getY())) {//jeżeli figura zasłaniająca
                    // króla doprowadzi do tego że król będzie pod szachem, to ruch nie jest zezwolony
                    chessPieceInUse.move(actualChessPieceX, actualChessPieceY);
                    return false;
                }
            }
            chessPieceInUse.move(actualChessPieceX, actualChessPieceY);
            return true;
        }
        return true;
        // TODO: 17.01.2024 dodać funkcjonalność - jeżeli król jest pod szachem to inna figura może się ruszyć
        // todo jeżeli będzie biła szachującą figurę
    }

    private boolean isValidPawnMove(ChessPiece pawn, int x, int y) {
        int deltaX = Math.abs(x - pawn.getX());
        int deltaY = Math.abs(y - pawn.getY());
        boolean diagonalMove = deltaX == deltaY;
        if (diagonalMove) {
            return !isFreeField(x, y);
        } else {
            return isClearLine(pawn, x, y) && isFreeField(x, y);
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

    private boolean isValidKingMove(ChessPiece king, int x, int y) {
        return isValidCastling(king, x) || isValidNormalMove(king, x, y);
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

    private boolean isValidNormalMove(ChessPiece king, int x, int y) {
        if (isCastling(king, x)) {
            return false;
        }
        return notInCheck(king, x, y);
    }

    private boolean notInCheck(ChessPiece king, int x, int y) {
        int actualKingX = king.getX();
        int actualKingY = king.getY();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(king.getEnemyColor());
        enemyChessPieces.removeIf(enemyChessPiece -> enemyChessPiece.getX() == x && enemyChessPiece.getY() == y);
        king.move(x, y); // CHECK : 17.01.2024 czy mogę tutaj wywołać tą metodę?
        for (ChessPiece enemyChessPiece : enemyChessPieces) {
            if (fieldIsDefending(enemyChessPiece, x, y)) {
                king.move(actualKingX, actualKingY);
                return false;
            }
        }
        king.move(actualKingX, actualKingY);
        return true;
    }

    private boolean fieldIsDefending(ChessPiece defendingChessPiece, int x, int y) {
        switch (defendingChessPiece.getType()) {
            case PAWN:
                Pawn pawn = (Pawn) defendingChessPiece;
                return pawn.isAttackingField(x, y);
            case KNIGHT:
            case ROOK:
            case RUNNER:
            case QUEEN:
                return defendingChessPiece.isCorrectMovement(x, y) && isClearLine(defendingChessPiece, x, y);
            case KING:
                return kingDefendingField(defendingChessPiece, x, y);
        }
        return false;
    }

    private boolean kingDefendingField(ChessPiece king, int x, int y) {
        int deltaX = Math.abs(king.getX() - x);
        int deltaY = Math.abs(king.getY() - y);
        return (deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1) || (deltaX == 1 && deltaY == 1);
    }

    private boolean isCastling(ChessPiece king, int x) {
        return Math.abs(king.getX() - x) == 2;
    }

    private boolean isClearCastlingLine(ChessPiece king, int x) {
        ChessPieceColor enemyColor = king.getEnemyColor();
        List<ChessPiece> enemyChessPieces = repository.getChessPieces(enemyColor);
        int startXLineToRightSide = x == 6 ? 5 : 2;
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