package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class Pawn extends ChessPiece {
    private int pawnMoves = 2;

    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        int deltaX = endCordsVector.x - x;
        int deltaY = endCordsVector.y - y;
        if ((getColor() == ChessPieceColor.WHITE && Integer.signum(deltaY) < 0) || // TODO: 10.10.2023 w tym momencie białe zawsze na dole. Zmienić w przyszłości
                (getColor() == ChessPieceColor.BLACK && Integer.signum(deltaY) > 0)) {
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
