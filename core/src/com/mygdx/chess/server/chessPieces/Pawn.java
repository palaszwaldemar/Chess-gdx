package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class Pawn extends ChessPiece {
    private boolean moved = false;

    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        int deltaX = endCordsVector.x - x;
        int deltaY = endCordsVector.y - y;
        if (!isMoveForward(deltaY)) {
            return false;
        }
        if (moved) {
            return isOneStep(deltaX, deltaY);
        }
        return isOneStep(deltaX, deltaY) || isTwoSteps(deltaX, deltaY);
    }

    @Override
    public void move(CordsVector endCordsVector) {
        super.move(endCordsVector);
        moved = true;
    }

    // TODO: 10.10.2023 w tym momencie białe zawsze na dole. Zmienić w przyszłości
    private boolean isMoveForward(int deltaY) {
        return (getColor() != ChessPieceColor.WHITE || Integer.signum(deltaY) >= 0) &&
                (getColor() != ChessPieceColor.BLACK || Integer.signum(deltaY) <= 0);
    }

    private boolean isOneStep(int deltaX, int deltaY) {
        return Math.abs(deltaX) <= 1 && Math.abs(deltaY) == 1;
    }

    private boolean isTwoSteps(int deltaX, int deltaY) {
        return Math.abs(deltaX) == 0 && Math.abs(deltaY) == 2;
    }
}
