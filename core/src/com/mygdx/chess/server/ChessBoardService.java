package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardService {
    private final List<ChessPiece> chessPieces = new ArrayList<>();

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
