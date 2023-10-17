package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class Runner extends ChessPiece {
    public Runner(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.RUNNER, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        int deltaX = Math.abs(x - endCordsVector.x);
        int deltaY = Math.abs(y - endCordsVector.y);
        return deltaX == deltaY;
    }
}
