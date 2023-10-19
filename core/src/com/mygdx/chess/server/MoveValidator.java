package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    boolean isCanMove(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        return isOnTheBoard(endCordsVector) &&
                isOpponentHere(chessPieceInUse, endCordsVector) &&
                isClearLineOrCorrectPawnMove(chessPieceInUse, endCordsVector) &&
                chessPieceInUse.isCorrectMovement(endCordsVector);
    }

    private boolean isOnTheBoard(CordsVector endCordsVector) {
        return endCordsVector.x >= 0 && endCordsVector.x <= 7 &&
                endCordsVector.y >= 0 && endCordsVector.y <= 7;
    }

    private boolean isOpponentHere(ChessPiece chessPieceInUse, CordsVector endCordsVector) { // CHECK : 17.10.2023 zmieniłem nazwę metody, zmieniłem "color" na ChessPiece
        boolean isOpponentHere = true;
        for (ChessPiece piece : chessPieces) { //todo okazja do zastosowania Streamów
            if (piece.getX() == endCordsVector.x && piece.getY() == endCordsVector.y) {
                if (piece.getColor().equals(chessPieceInUse.getColor())) {
                    isOpponentHere = false;
                }
            }
        }
        return isOpponentHere;
    }

    private boolean isClearLineOrCorrectPawnMove(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isCorrectPawnMove(chessPieceInUse, endCordsVector);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isClearLine(chessPieceInUse, endCordsVector);
        }
        return true;
    }

    private boolean isCorrectPawnMove(ChessPiece chessPieceInUse, CordsVector endCordsVector) {// CHECK : 17.10.2023 poprawiono: pionek przy
        // CHECK : 17.10.2023  pierwszym ruchu o dwa pola nie może zostać przesunięty jeżeli na pierwszym polu jest inny pionek. Czy metoda czytelna?
        int deltaX = Math.abs(endCordsVector.x - chessPieceInUse.getX());
        int deltaY = Math.abs(endCordsVector.y - chessPieceInUse.getY());

        if (deltaX == deltaY) {
            return !isFieldFree(endCordsVector.x, endCordsVector.y);
        } else if (isClearLine(chessPieceInUse, endCordsVector)) {
            return isFieldFree(endCordsVector.x, endCordsVector.y);
        } else {
            return false;
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
}
