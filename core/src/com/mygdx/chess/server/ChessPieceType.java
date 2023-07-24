package com.mygdx.chess.server;

enum ChessPieceType {
    PAWN("pawn"),
    ROOK("rook"),
    KNIGHT("knight"),
    RUNNER("runner"),
    QUEEN("queen"),
    KING("king");

    private final String name;

    ChessPieceType(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
