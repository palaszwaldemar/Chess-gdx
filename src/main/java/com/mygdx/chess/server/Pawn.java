package com.mygdx.chess.server;

import static com.mygdx.chess.server.ChessPieceColor.BLACK;
import static com.mygdx.chess.server.ChessPieceColor.WHITE;

class Pawn extends ChessPiece {
    Pawn(ChessPieceDto chessPieceDto) {
        super(chessPieceDto);
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
    void move(int x, int y) {
        super.move(x, y);
        moved = true;
    }

    boolean isAttackingField(int x, int y) {
        int deltaX = x - this.x;
        int deltaY = y - this.y;
        return isCorrectMovement(x, y) && Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1;
    }

    private boolean isMoveBack(int deltaY) {
        return (getColor() == WHITE && Integer.signum(deltaY) < 0) ||
            (getColor() == BLACK && Integer.signum(deltaY) > 0);
    }

    private boolean isOneStep(int deltaX, int deltaY) {
        return Math.abs(deltaX) <= 1 && Math.abs(deltaY) == 1;
    }

    private boolean isTwoSteps(int deltaX, int deltaY) {
        return Math.abs(deltaX) == 0 && Math.abs(deltaY) == 2;
    }
}
