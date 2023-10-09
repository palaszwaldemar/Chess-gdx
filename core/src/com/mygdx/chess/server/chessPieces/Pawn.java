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
        boolean correctMove;
        if (getColor().equals(ChessPieceColor.WHITE)) {
            correctMove = getX() == endCordsVector.x && endCordsVector.y - getY() <= pawnMoves && endCordsVector.y > getY();
        } else {
            correctMove = getX() == endCordsVector.x && endCordsVector.y - getY() >= -pawnMoves && endCordsVector.y < getY();
        }
        if (correctMove) pawnMoves = 1; // TODO: 09.10.2023 czy powinienem dodać tutaj wyjątek InvalidMoveException?
        return correctMove;
    }
}
