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

    boolean isCorrectMovement(ChessPieceType type, int chessPieceX, int chessPieceY, int newChessPieceX, int newChessPieceY) {
        boolean straightLineMoving = chessPieceX == newChessPieceX || chessPieceY == newChessPieceY;
        boolean diagonallyMoving = Math.abs(chessPieceX - newChessPieceX) == Math.abs(chessPieceY - newChessPieceY);
        if (type.equals(ChessPieceType.ROOK)) {
            return straightLineMoving;
        }
        if (type.equals(ChessPieceType.RUNNER)) {
            return diagonallyMoving;
        }
        if (type.equals(ChessPieceType.KING)) {
            return Math.abs(chessPieceX - newChessPieceX) <= 1 && Math.abs(chessPieceY - newChessPieceY) <= 1;
        }
        if (type.equals(ChessPieceType.QUEEN)) {
            return straightLineMoving || diagonallyMoving;
        }
        if (type.equals(ChessPieceType.KNIGHT)) {
            return (Math.abs(chessPieceX - newChessPieceX) == 2 && Math.abs(chessPieceY - newChessPieceY) == 1) ||
                    (Math.abs(chessPieceY - newChessPieceY) == 2 && Math.abs(chessPieceX - newChessPieceX) == 1);
        }
        if (type.equals(ChessPieceType.PAWN)) {
            return chessPieceX == newChessPieceX && newChessPieceY - chessPieceY == 1;
        }
        return false;
    }
}
