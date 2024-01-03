package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Runner extends ChessPiece {
    public Runner(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.RUNNER, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        int deltaX = Math.abs(x - newX);
        int deltaY = Math.abs(y - newY);
        return deltaX == deltaY;
    }
}
