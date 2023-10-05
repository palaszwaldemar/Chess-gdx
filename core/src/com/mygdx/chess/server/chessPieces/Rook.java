package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPiece;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Rook extends ChessPiece {
    public Rook(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.ROOK, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        return getX() == xEndPosition || getY() == yEndPosition;
    }
}
