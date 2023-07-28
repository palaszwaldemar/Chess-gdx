package com.mygdx.chess.server;

public class ChessPiece {
    private int x;
    private int y;
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

    public void setGridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getStringImage() {
        return stringImage;
    }
}
