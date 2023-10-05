package com.mygdx.chess.server;

public class Knight extends ChessPiece{
    public Knight(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KNIGHT, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        return (Math.abs(getX() - xEndPosition) == 2 && Math.abs(getY() - yEndPosition) == 1) ||
                (Math.abs(getY() - yEndPosition) == 2 && Math.abs(getX() - xEndPosition) == 1);
    }
}
