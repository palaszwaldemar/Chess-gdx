package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Knight extends ChessPiece {
    public Knight(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KNIGHT, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int xEndPosition, int yEndPosition) {
        return (Math.abs(getX() - xEndPosition) == 2 && Math.abs(getY() - yEndPosition) == 1) ||
                (Math.abs(getY() - yEndPosition) == 2 && Math.abs(getX() - xEndPosition) == 1);
    }
}
