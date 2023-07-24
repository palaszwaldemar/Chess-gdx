package com.mygdx.chess.server;

enum ChessPieceColor {
    BLACK("Black"),
    WHITE("White");

    private final String name;

    ChessPieceColor(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
