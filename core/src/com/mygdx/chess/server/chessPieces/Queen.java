package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.EndCordsVector;

public class Queen extends ChessPiece {
    public Queen(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.QUEEN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(EndCordsVector endCordsVector) {
        int deltaX = Math.abs(getX() - endCordsVector.x);
        int deltaY = Math.abs(getY() - endCordsVector.y);
        return (getX() == endCordsVector.x || getY() == endCordsVector.y) || (deltaX == deltaY);
    }
}
