package com.mygdx.chess.client;

import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessboardGroup chessboardGroup;

    public Controller(ChessboardGroup chessboardGroup) {
        this.chessboardGroup = chessboardGroup;
    }

    void startGame() {
        chessboardGroup.createActors(service.getChessPieces());
    }

    boolean checkValidPosition(float x, float y, ChessPieceType type, ChessPieceColor color, int xCord, int yCord, int chessPieceX, int chessPieceY) {
        return service.isValidPosition(x, y,type , color, xCord, yCord, chessPieceX, chessPieceY);
    }
}
