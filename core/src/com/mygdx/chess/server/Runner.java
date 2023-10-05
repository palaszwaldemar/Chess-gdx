package com.mygdx.chess.server;

public class Runner extends ChessPiece{
    public Runner(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.RUNNER, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        return Math.abs(getX() - xEndPosition) == Math.abs(getY() - yEndPosition);
    }
}
