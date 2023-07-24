package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardService {
    private final List<ChessPiece> chessPieces;

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces = new ArrayList<>(piecesFactory.getChessPieces());
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
