package com.mygdx.chess.server;

public class ChessPiece {
    private int x;
    private int y;
    private final ChessPieceColor color;
    private final ChessPieceType type;

    public ChessPiece(ChessPieceType type, ChessPieceColor color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
    }

    public void setPosition(int x, int y) {
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
}

// TODO: 07.09.2023 Klasa ta ma być klasą abstr. Zrobić klasy po niej dziedziczące - typy figur
