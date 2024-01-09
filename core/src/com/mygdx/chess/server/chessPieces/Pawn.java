package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Pawn extends ChessPiece {
    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int newX, int newY) {
        int deltaX = newX - x;
        int deltaY = newY - y;
        if (isMoveBack(deltaY)) {
            return false;
        }
        if (moved) {
            return isOneStep(deltaX, deltaY);
        }
        return isOneStep(deltaX, deltaY) || isTwoSteps(deltaX, deltaY);
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
        moved = true;
    }

    public boolean attackField(int x, int y) {
        int deltaX = x - this.x;
        int deltaY = y - this.y;
        // Sprawdzenie kierunku bicia
        if (isMoveBack(deltaY)) {
            return false;
        }
        // Sprawdzenie czy bicie po diagonali
        return Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1;
    }
    // TODO: 10.10.2023 w tym momencie białe zawsze na dole. Zmienić w przyszłości

    private boolean isMoveBack(int deltaY) {// CHECK : 09.01.2024 zmiana nazwy metody?
        return (getColor() == ChessPieceColor.WHITE && Integer.signum(deltaY) < 0) ||
            (getColor() == ChessPieceColor.BLACK && Integer.signum(deltaY) > 0);
    }

    private boolean isOneStep(int deltaX, int deltaY) {
        return Math.abs(deltaX) <= 1 && Math.abs(deltaY) == 1;
    }

    private boolean isTwoSteps(int deltaX, int deltaY) {
        return Math.abs(deltaX) == 0 && Math.abs(deltaY) == 2;
    }
}
