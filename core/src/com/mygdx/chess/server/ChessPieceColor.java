package com.mygdx.chess.server;

enum ChessPieceColor {
    BLACK("Black", 7, 6),
    WHITE("White", 0, 1);

    private final String name;
    private final int yFiguresPosition;
    private final int yPawnsPosition;

    ChessPieceColor(String name, int yFiguresPosition, int yPawnsPosition) {
        this.name = name;
        this.yFiguresPosition = yFiguresPosition;
        this.yPawnsPosition = yPawnsPosition;

    }

    String getName() {
        return name;
    }

    public int getYFiguresPosition() {
        return yFiguresPosition;
    }

    public int getYPawnsPosition() {
        return yPawnsPosition;
    }
}
