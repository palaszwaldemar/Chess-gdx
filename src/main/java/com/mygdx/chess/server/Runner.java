package com.mygdx.chess.server;

class Runner extends ChessPiece {
    Runner(ChessPieceDto chessPieceDto) {
        super(chessPieceDto);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        int deltaX = Math.abs(x - newX);
        int deltaY = Math.abs(y - newY);
        return deltaX == deltaY;
    }
}
