package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Pawn extends ChessPiece {
    private int pawnMoves = 2;

    public Pawn(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.PAWN, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(int xEndPosition, int yEndPosition) {
        boolean correctMove;
        if (getColor().equals(ChessPieceColor.WHITE)) {
            correctMove = getX() == xEndPosition && yEndPosition - getY() <= pawnMoves && yEndPosition > getY();
        } else {
            correctMove = getX() == xEndPosition && yEndPosition - getY() >= -pawnMoves && yEndPosition < getY();
        }
        if (correctMove) pawnMoves = 1;
        return correctMove;
    }
}
