package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.EndCordsVector;

public class Knight extends ChessPiece {
    public Knight(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KNIGHT, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(EndCordsVector endCordsVector) {
        return (Math.abs(getX() - endCordsVector.x) == 2 && Math.abs(getY() - endCordsVector.y) == 1) ||
                (Math.abs(getY() - endCordsVector.y) == 2 && Math.abs(getX() - endCordsVector.x) == 1);
    }
}
