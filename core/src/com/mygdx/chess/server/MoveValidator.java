package com.mygdx.chess.server;

import com.mygdx.chess.client.GuiParams;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public boolean isOnTheBoard(float x, float y) {
        return x >= 0 && x <= GuiParams.CHESSBOARD_WIDTH &&
                y >= 0 && y <= GuiParams.CHESSBOARD_HEIGHT;
    }

    boolean isSameColorPieceHere(ChessPieceColor color, int xCord, int yCord) {
        boolean isSameColorPieceHere = true;
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == xCord && piece.getY() == yCord) {
                if (piece.getColor().equals(color)) {
                    isSameColorPieceHere = false;
                }
            }
        }
        return isSameColorPieceHere;
    }
}
