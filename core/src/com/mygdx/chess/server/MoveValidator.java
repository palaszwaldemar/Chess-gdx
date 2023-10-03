package com.mygdx.chess.server;

import com.mygdx.chess.client.GuiParams;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    boolean isCorrectMovement(ChessPiece chessPieceInUse, int xEndPosition, int yEndPosition) { // TODO: 07.09.2023 do wykasowania
        boolean straightLineMoving = chessPieceInUse.getX() == xEndPosition || chessPieceInUse.getY() == yEndPosition;
        boolean diagonallyMoving = Math.abs(chessPieceInUse.getX() - xEndPosition) == Math.abs(chessPieceInUse.getY() - yEndPosition);
        if (chessPieceInUse.getType().equals(ChessPieceType.ROOK)) {
            return straightLineMoving;
        }
        if (chessPieceInUse.getType().equals(ChessPieceType.RUNNER)) {
            return diagonallyMoving;
        }
        if (chessPieceInUse.getType().equals(ChessPieceType.KING)) {
            return Math.abs(chessPieceInUse.getX() - xEndPosition) <= 1 && Math.abs(chessPieceInUse.getY() - yEndPosition) <= 1;
        }
        if (chessPieceInUse.getType().equals(ChessPieceType.QUEEN)) {
            return straightLineMoving || diagonallyMoving;
        }
        if (chessPieceInUse.getType().equals(ChessPieceType.KNIGHT)) {
            return (Math.abs(chessPieceInUse.getX() - xEndPosition) == 2 && Math.abs(chessPieceInUse.getY() - yEndPosition) == 1) ||
                    (Math.abs(chessPieceInUse.getY() - yEndPosition) == 2 && Math.abs(chessPieceInUse.getX() - xEndPosition) == 1);
        }
        if (chessPieceInUse.getType().equals(ChessPieceType.PAWN) && chessPieceInUse.getColor().equals(ChessPieceColor.WHITE)) {
            return chessPieceInUse.getX() == xEndPosition && yEndPosition - chessPieceInUse.getY() == 1;
        } else {
            return chessPieceInUse.getX() == xEndPosition && yEndPosition - chessPieceInUse.getY() == -1;
        }
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
