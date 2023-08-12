package com.mygdx.chess.client;

import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPiece;
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

    public boolean chessPieceHasCorrectPlacement(float x, float y, float widthOfBoard, float heightOfBoard, ChessPieceColor color) {
        return service.canMoveChessPiece(x, y, widthOfBoard, heightOfBoard, color);
    }
}
