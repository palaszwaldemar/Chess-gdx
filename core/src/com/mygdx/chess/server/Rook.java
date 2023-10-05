package com.mygdx.chess.server;

public class Rook extends ChessPiece{
    public Rook(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.ROOK, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        return false;
    }
}
