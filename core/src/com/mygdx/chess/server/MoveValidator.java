package com.mygdx.chess.server;

import com.mygdx.chess.client.GuiParams;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public boolean isOnTheBoard(float vectorX, float vectorY) {
        return vectorX >= 0 && vectorX <= GuiParams.CHESSBOARD_WIDTH &&
                vectorY >= 0 && vectorY <= GuiParams.CHESSBOARD_HEIGHT;
    }

    boolean isSameColorPieceHere(ChessPiece chessPieceInUse, int xEndPosition, int yEndPosition) {
        ChessPieceColor color = chessPieceInUse.getColor();
        boolean isNoSameColorPieceHere = true;
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == xEndPosition && piece.getY() == yEndPosition) {
                if (piece.getColor().equals(color)) {
                    isNoSameColorPieceHere = false;
                }
            }
        }
        return isNoSameColorPieceHere;
    }
}
