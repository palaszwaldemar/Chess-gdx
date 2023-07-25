package com.mygdx.chess.client;

import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();

    List<ChessPiece> listOfPieces() {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(service.getWhiteChessPieces());
        chessPieces.addAll(service.getBlackChessPieces());
        return chessPieces;
    }
}
