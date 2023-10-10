package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.EndCordsVector;

public class Runner extends ChessPiece {
    public Runner(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.RUNNER, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(EndCordsVector endCordsVector) {
        int deltaX = Math.abs(getX() - endCordsVector.x);
        int deltaY = Math.abs(getY() - endCordsVector.y);
        return deltaX == deltaY;
    }
}
