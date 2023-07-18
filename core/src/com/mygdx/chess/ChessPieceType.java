package com.mygdx.chess;

public enum ChessPieceType {
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

    public String getName() {
        return name;
    }
}
