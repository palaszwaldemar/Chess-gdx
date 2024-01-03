package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Rook extends ChessPiece {
    public Rook(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.ROOK, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        return x == newX || y == newY;
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
        moved = true;
    }
}
