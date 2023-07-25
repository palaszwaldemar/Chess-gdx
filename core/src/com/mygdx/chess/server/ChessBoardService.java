package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardService {
    private final List<ChessPiece> whiteChessPieces;
    private final List<ChessPiece> blackChessPieces;

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        whiteChessPieces = new ArrayList<>(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        blackChessPieces = new ArrayList<>(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
    }

    public List<ChessPiece> getWhiteChessPieces() {
        return whiteChessPieces;
    }

    public List<ChessPiece> getBlackChessPieces() {
        return blackChessPieces;
    }
}
