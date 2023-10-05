package com.mygdx.chess.server;

public enum ChessPieceColor {
    BLACK(7, 6),
    WHITE(0, 1);

    private final int yFiguresPosition;
    private final int yPawnsPosition;

    ChessPieceColor(int yFiguresPosition, int yPawnsPosition) {
        this.yFiguresPosition = yFiguresPosition;
        this.yPawnsPosition = yPawnsPosition;

    }

    public int getYFiguresPosition() {
        return yFiguresPosition;
    }

    public int getYPawnsPosition() {
        return yPawnsPosition;
    }
}
