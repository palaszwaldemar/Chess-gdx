package com.mygdx.chess.server.chessPieces;

import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.CordsVector;

public class King extends ChessPiece {
    private boolean moved = false;

    public King(ChessPieceColor color, int x, int y) {
        super(ChessPieceType.KING, color, x, y);
    }

    @Override
    public boolean isCorrectMovement(CordsVector endCordsVector) {
        int deltaX = Math.abs(x - endCordsVector.x);
        int deltaY = Math.abs(y - endCordsVector.y);
        boolean oneStepMove = (deltaX <= 1 && deltaY <= 1) && (deltaX + deltaY != 0);
        boolean castlingMove = deltaY == 0 && deltaX == 2;
        if (moved) {
            return oneStepMove;
        }
        return oneStepMove || castlingMove;
    }

    @Override
    public void move(CordsVector endCordsVector) {
        super.move(endCordsVector);
        moved = true;
    }
}
