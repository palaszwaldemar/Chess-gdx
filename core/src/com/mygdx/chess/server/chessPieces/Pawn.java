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
        int deltaX = endCordsVector.x - getX();
        int deltaY = endCordsVector.y - getY();
        if ((getColor() == ChessPieceColor.WHITE && Integer.signum(deltaY) < 0) ||
                (getColor() == ChessPieceColor.BLACK && Integer.signum(deltaY) > 0)) { // TODO: 10.10.2023 w tym momencie białe zawsze na dole. Zmienić w przyszłości
            return false;
        }
        if (Math.abs(deltaY) == 2 && pawnMoves == 2 && Math.abs(deltaX) == 0) {
            pawnMoves = 1;
            return true;
        }
        if (Math.abs(deltaY) == 1 && Math.abs(deltaX) <= 1) {
            pawnMoves = 1;
            return true;
        }
        return false;
    }
}
