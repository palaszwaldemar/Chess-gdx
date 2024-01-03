package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Queen extends ChessPiece {
    public Queen(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.QUEEN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        int deltaX = Math.abs(x - newX);
        int deltaY = Math.abs(y - newY);
        return (x == newX || y == newY) || (deltaX == deltaY);
    }
}
