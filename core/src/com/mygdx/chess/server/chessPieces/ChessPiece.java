package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

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

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean wasNotMoved() {
        return !moved;
    }

    public abstract boolean isCorrectMovement(int x, int y);

    public boolean hasSameColor(ChessPiece chessPiece) {
        return color == chessPiece.getColor();
    }

    public ChessPieceColor getEnemyColor() {
        ChessPieceColor enemyColor;
        if (hasColor(ChessPieceColor.WHITE)) {
            enemyColor = ChessPieceColor.BLACK;
        } else {
            enemyColor = ChessPieceColor.WHITE;
        }
        return enemyColor;
    }

    public boolean hasColor(ChessPieceColor color) {
        return this.color == color;
    }

    public boolean hasType(ChessPieceType type) {
        return this.type == type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
            "x=" + x +
            ", y=" + y +
            ", color=" + color +
            ", type=" + type +
            ", moved=" + moved +
            '}';
    }
}
