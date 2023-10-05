package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPiece;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class King extends ChessPiece {
    public King(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KING, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        return Math.abs(getX() - xEndPosition) <= 1 && Math.abs(getY() - yEndPosition) <= 1;
    }
}
