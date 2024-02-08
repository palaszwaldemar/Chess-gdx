package com.mygdx.chess.server;

class Rook extends ChessPiece {
    Rook(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.ROOK, color, x, y);
    }

    int getCastlingX() {
        return x == 7 ? 5 : 3;
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        return x == newX || y == newY;
    }

    @Override
    void move(int x, int y) {
        super.move(x, y);
        moved = true;
    }
}
