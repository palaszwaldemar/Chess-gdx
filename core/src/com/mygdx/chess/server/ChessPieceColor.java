package com.mygdx.chess.server;

public enum ChessPieceColor {
    BLACK("Black"),
    WHITE("White");

    private final String name;

    ChessPieceColor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
