package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.EndCordsVector;

public class Pawn extends ChessPiece {
    private int pawnMoves = 2;

    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(EndCordsVector endCordsVector) {
        int deltaX = Math.abs(endCordsVector.x - getX());
        int deltaY = Math.abs(endCordsVector.y - getY());
        if (deltaY == 2 && pawnMoves == 2 && deltaX == 0) {
            pawnMoves = 1;
            return true;
        }
        if (deltaY == 1 && deltaX <= 1) {
            pawnMoves = 1;
            return true;
        }
        return false;
    }
}
