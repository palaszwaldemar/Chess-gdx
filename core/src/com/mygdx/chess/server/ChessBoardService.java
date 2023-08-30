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

    public boolean isValidPosition(float x, float y,ChessPieceType type, ChessPieceColor color, int xCord, int yCord, int chessPieceX, int chessPieceY) {
        return moveValidator.isOnTheBoard(x, y) && moveValidator.isSameColorPieceHere(color, xCord, yCord) && moveValidator.isCorrectMovement(type, chessPieceX, chessPieceY, xCord, yCord);
    }
}
