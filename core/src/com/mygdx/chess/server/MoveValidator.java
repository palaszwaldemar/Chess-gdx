package com.mygdx.chess.server;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.chess.client.GuiParams;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public boolean isOnTheBoard(Vector2 mouseDropPosition) {
        return mouseDropPosition.x >= 0 && mouseDropPosition.x <= GuiParams.CHESSBOARD_WIDTH &&
                mouseDropPosition.y >= 0 && mouseDropPosition.y <= GuiParams.CHESSBOARD_HEIGHT;
    }

    boolean isNoSameColorPieceHere(ChessPieceColor color, EndCordsVector endCordsVector) {
        boolean isNoSameColorPieceHere = true;
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == endCordsVector.x && piece.getY() == endCordsVector.y) {
                if (piece.getColor().equals(color)) {
                    isNoSameColorPieceHere = false;
                }
            }
        }
        return isNoSameColorPieceHere;
    }

    boolean isClearLineOrCorrectPawnMove(ChessPiece chessPieceInUse, EndCordsVector endCordsVector) {
        switch (chessPieceInUse.getType()) {
            case PAWN:
                return isCorrectOrFreeField(chessPieceInUse, endCordsVector);
            case ROOK:
            case RUNNER:
            case QUEEN:
                return isFreeLine(chessPieceInUse, endCordsVector);
        }
        return true;
    }

    private boolean isCorrectOrFreeField(ChessPiece chessPieceInUse, EndCordsVector endCordsVector) {
        int deltaX = endCordsVector.x - chessPieceInUse.getX();
        int deltaY = endCordsVector.y - chessPieceInUse.getY();
        if (Math.abs(deltaX) == Math.abs(deltaY)) {
            return isFiledNotFree(endCordsVector.x, endCordsVector.y);
        } else {
            return !isFiledNotFree(endCordsVector.x, endCordsVector.y);
        }
    }

    private boolean isFreeLine(ChessPiece chessPieceInUse, EndCordsVector endCordsVector) {
        int startX = chessPieceInUse.getX();
        int startY = chessPieceInUse.getY();
        int deltaX = endCordsVector.x - startX;
        int deltaY = endCordsVector.y - startY;
        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int x = startX + i * Integer.signum(deltaX);
            int y = startY + i * Integer.signum(deltaY);
            if (isFiledNotFree(x, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFiledNotFree(int x, int y) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
