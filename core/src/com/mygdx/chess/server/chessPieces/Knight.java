package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class Knight extends ChessPiece {
    public Knight(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KNIGHT, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        int deltaX = Math.abs(x - endCordsVector.x);
        int deltaY = Math.abs(y - endCordsVector.y);
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }
}
