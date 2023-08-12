package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardService {
    private final List<ChessPiece> chessPieces = new ArrayList<>();
    private final MoveValidator moveValidator;

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
        moveValidator = new MoveValidator(chessPieces);
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }

    boolean isValidPlacement() {
        return true;
    }

    boolean canMoveOnThisPlace(ChessPiece chessPiece) {
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == chessPiece.getX() && piece.getY() == chessPiece.getY()) {
                if (piece.getColor() == chessPiece.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveChessPiece(float x, float y, float widthOfBoard, float heightOfBoard, ChessPieceColor color) {
        return moveValidator.chessPieceIsOnTheBoard(x, y, widthOfBoard, heightOfBoard) && moveValidator.isItFreePlace(x, y, color);
    }
}
