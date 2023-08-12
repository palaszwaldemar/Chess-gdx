package com.mygdx.chess.client;

import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPieceColor;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessboardGroup chessboardGroup;

    public Controller(ChessboardGroup chessboardGroup) {
        this.chessboardGroup = chessboardGroup;
    }

    void startGame() {
        chessboardGroup.createActors(service.getChessPieces());
    }

    boolean checkValidPosition(float x, float y, ChessPieceColor color, int xCord, int yCord) {
        return service.isValidPosition(x, y, color, xCord, yCord);
    }
}
