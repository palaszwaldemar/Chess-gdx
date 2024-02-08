package com.mygdx.chess.server;

class King extends ChessPiece {
    King(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KING, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        int deltaX = Math.abs(x - newX);
        int deltaY = Math.abs(y - newY);
        boolean oneStepMove = (deltaX <= 1 && deltaY <= 1) && (deltaX + deltaY != 0);
        boolean castlingMove = deltaY == 0 && deltaX == 2;
        if (moved) {
            return oneStepMove;
        }
        return oneStepMove || castlingMove;
    }

    @Override
    void move(int x, int y) {
        super.move(x, y);
        moved = true;
    }
}
