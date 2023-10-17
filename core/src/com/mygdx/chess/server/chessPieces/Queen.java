package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class Queen extends ChessPiece {
    public Queen(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.QUEEN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        int deltaX = Math.abs(x - endCordsVector.x);
        int deltaY = Math.abs(y - endCordsVector.y);
        return (x == endCordsVector.x || y == endCordsVector.y) || (deltaX == deltaY);
    }
}
