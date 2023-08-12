package com.mygdx.chess.server;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public boolean chessPieceIsOnTheBoard(float x, float y, float widthOfBoard, float heightOfBoard) {
        return x >= 0 && x <= widthOfBoard &&
                y >= 0 && y <= heightOfBoard;
    }

    public boolean isItFreePlace(float x, float y, ChessPieceColor color) {
        boolean isItFreePlace = true;
        for (ChessPiece piece : chessPieces) {
            System.out.println("x = " + x + ", y = " + y);
            System.out.println("chessPiece z listy: x = " + piece.getX() + ", y = " + piece.getY());
            if (piece.getX() == x && piece.getY() == y) {
                if (piece.getColor() == color) {
                    isItFreePlace = false;
                }
            }
        }
        return isItFreePlace;
    }
}
