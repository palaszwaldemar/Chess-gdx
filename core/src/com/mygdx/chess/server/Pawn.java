package com.mygdx.chess.server;

public class Pawn extends ChessPiece {

    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean correctMovement(int xEndPosition, int yEndPosition) {
        if (getColor().equals(ChessPieceColor.WHITE)) {
            return getX() == xEndPosition && yEndPosition - getY() == 1;
        } else {
            return getX() == xEndPosition && yEndPosition - getY() == -1;
        }
        // TODO: 05.10.2023 w tym momencie miejsce białych i czarnych jest ustawione na sztywno (białe na dole,
        //  czarne na górze. Zmienić tą implementację, kiedy wprowadzona zostanie funkcjonalność: wybór koloru figur
        //  przez gracza
    }
}
