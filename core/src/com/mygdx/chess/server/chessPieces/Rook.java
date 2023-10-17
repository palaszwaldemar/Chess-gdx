package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class Rook extends ChessPiece {
    public Rook(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.ROOK, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        return x == endCordsVector.x || y == endCordsVector.y;
    }
}
