package com.mygdx.chess.client;

import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPiece;

import java.util.List;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();

    List<ChessPiece> listOfPieces() {
        return service.getChessPieces();
    }
}
