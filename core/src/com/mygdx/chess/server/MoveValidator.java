package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    boolean canMove(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        return isOnTheBoard(endCordsVector) &&
                isNotSameColorFigureHere(chessPieceInUse, endCordsVector) &&
                chessPieceInUse.isCorrectMovement(endCordsVector) &&
                isValidMoveForChessPiece(chessPieceInUse, endCordsVector);
    }

    private boolean isOnTheBoard(CordsVector endCordsVector) {
        return endCordsVector.x >= 0 && endCordsVector.x <= 7 && endCordsVector.y >= 0 &&
                endCordsVector.y <= 7;
    }

    private boolean isNotSameColorFigureHere(ChessPiece chessPieceInUse,
                                             CordsVector endCordsVector) {
        boolean notSameColorFigureHere = true;
        for (ChessPiece piece : chessPieces) { //todo okazja do zastosowania Streamów
            if (piece.getX() == endCordsVector.x && piece.getY() == endCordsVector.y) {
                if (piece.getColor().equals(chessPieceInUse.getColor())) {
                    notSameColorFigureHere = false;
                }
            }
        }
        return notSameColorFigureHere;
    }

    private boolean isValidMoveForChessPiece(ChessPiece chessPieceInUse,
                                             CordsVector endCordsVector) {
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isCorrectPawnMove(chessPieceInUse, endCordsVector);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLine(chessPieceInUse, endCordsVector);
            case KING:
                return isValidCastlingOrNormalMove(chessPieceInUse, endCordsVector);
        }
        return true;
    }

    private boolean isCorrectPawnMove(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        int deltaX = Math.abs(endCordsVector.x - chessPieceInUse.getX());
        int deltaY = Math.abs(endCordsVector.y - chessPieceInUse.getY());
        boolean diagonalMove = deltaX == deltaY;
        if (diagonalMove) {
            return !isFieldFree(endCordsVector.x, endCordsVector.y);
        } else {
            return isClearLine(chessPieceInUse, endCordsVector) &&
                    isFieldFree(endCordsVector.x, endCordsVector.y);
        }
    }

    private boolean isFieldFree(int x, int y) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return false;
            }
        }
        return true;
    }

    private boolean isClearLine(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        int startX = chessPieceInUse.getX();
        int startY = chessPieceInUse.getY();
        int deltaX = endCordsVector.x - startX;
        int deltaY = endCordsVector.y - startY;
        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int x = startX + i * Integer.signum(deltaX);
            int y = startY + i * Integer.signum(deltaY);
            if (!isFieldFree(x, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCastlingOrNormalMove(ChessPiece king, CordsVector endCordsVector) {
        if (!isCastling(king, endCordsVector)) {
            return true;
        }
        if (isKingInCheck(king)) {
            return false;
        }
        int xRook = isRightCastling(king, endCordsVector) ? 7 : 0;
        return isValidCastling(king, xRook);
    }

    private boolean isCastling(ChessPiece king, CordsVector endCordsVector) {
        return Math.abs(king.getX() - endCordsVector.x) == 2;
    }

    private boolean isKingInCheck(ChessPiece king) {
        int x = king.getX();
        int y = king.getY();
        CordsVector kingCordsVector = new CordsVector(x, y);
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getColor() != king.getColor() &&
                    canMove(chessPiece, kingCordsVector)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightCastling(ChessPiece king, CordsVector endCordsVector) {
        return king.getX() < endCordsVector.x;
    }

    private boolean isValidCastling(ChessPiece king, int xRook) {
        return isValidCastlingByRook(king, xRook) && isClearCastlingLine(king, xRook);
    }

    private boolean isValidCastlingByRook(ChessPiece king, int xRook) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getType() == ChessPieceType.ROOK &&
                    chessPiece.getColor() == king.getColor() && chessPiece.getX() == xRook &&
                    !chessPiece.wasMoved()) {
                return true;
            }
        }
        return false;
    }

    private boolean isClearCastlingLine(ChessPiece king, int xRook) {
        int startXLineToRight = xRook == 7 ? 5 : 1; // CHECK : 12.12.2023 czy teraz nazwa zmiennej zrozumiała?
        int endXLineFromLeftSide = xRook == 7 ? 7 : 4; // CHECK : 12.12.2023 czy teraz nazwa zmiennej zrozumiała?
        for (int i = startXLineToRight; i < endXLineFromLeftSide; i++) {
            for (ChessPiece chessPiece : chessPieces) {
                CordsVector cordsVector = new CordsVector(i, king.getY());
                if (chessPieceInRoad(chessPiece, king, i)) {
                    return false;
                }
                if (chessPieceAttackField(chessPiece, king, cordsVector)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean chessPieceInRoad(ChessPiece chessPiece, ChessPiece king, int x) {
        return chessPiece.getX() == x && chessPiece.getY() ==
                king.getY();
    }

    private boolean chessPieceAttackField(ChessPiece chessPiece, ChessPiece king,
                                          CordsVector cordsVector) {
        return chessPiece.getColor() != king.getColor() && canMove(chessPiece, cordsVector);
    }
}
// TODO: 05.12.2023 stworzyć BoardRepository dla reprezentacji listy. Stworzyć odpowiednie metody potrzebne do przechodzenia po tej liście
