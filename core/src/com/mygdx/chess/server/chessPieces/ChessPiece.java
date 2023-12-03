package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public abstract class ChessPiece {
    int x;
    int y;
    private final ChessPieceColor color;
    private final ChessPieceType type;
    boolean moved;

    public ChessPiece(ChessPieceType type, ChessPieceColor color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
    }

    public void move(CordsVector endCordsVector) {
        x = endCordsVector.x;
        y = endCordsVector.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChessPieceColor getColor() {
        return color;
    }

    public ChessPieceType getType() {
        return type;
    }

    public boolean wasMoved() {
        return moved;
    }

    public abstract boolean isCorrectMovement(CordsVector endCordsVector);
}
