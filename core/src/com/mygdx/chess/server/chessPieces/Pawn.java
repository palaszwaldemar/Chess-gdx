package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Pawn extends ChessPiece {

    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int xEndPosition, int yEndPosition) {
        if (getColor().equals(ChessPieceColor.WHITE)) {
            return getX() == xEndPosition && yEndPosition - getY() == 1;
        } else {
            return getX() == xEndPosition && yEndPosition - getY() == -1;
        }
    }
}
