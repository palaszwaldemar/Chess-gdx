package com.mygdx.chess;

public class ChessPiece {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
