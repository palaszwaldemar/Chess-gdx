package com.mygdx.chess.server;

class Knight extends ChessPiece {
    Knight(ChessPieceDto chessPieceDto) {
        super(chessPieceDto);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        int deltaX = Math.abs(x - newX);
        int deltaY = Math.abs(y - newY);
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }
}
