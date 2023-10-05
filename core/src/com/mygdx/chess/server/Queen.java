package com.mygdx.chess.server;

public class Queen extends ChessPiece {
    public Queen(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.QUEEN, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        return (getX() == xEndPosition || getY() == yEndPosition) ||
                (Math.abs(getX() - xEndPosition) == Math.abs(getY() - yEndPosition));
    }
}
