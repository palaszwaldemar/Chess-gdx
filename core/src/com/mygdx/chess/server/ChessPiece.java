package com.mygdx.chess.server;

public class ChessPiece {
    private final int x;
    private final int y;
    private final String stringImage;

    public ChessPiece(ChessPieceType type, ChessPieceColor color, int x, int y) {
        this.x = x;
        this.y = y;
        stringImage = "chess_pieces/" + type.getName() + color.getName() + ".png";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getStringImage() {
        return stringImage;
    }
}
