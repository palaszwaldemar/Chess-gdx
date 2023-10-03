package com.mygdx.chess.client;

import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPiece;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessboardGroup chessboardGroup;

    public Controller(ChessboardGroup chessboardGroup) {
        this.chessboardGroup = chessboardGroup;
    }

    void startGame() {
        chessboardGroup.createActors(service.getChessPieces());
    }

    void move(ChessPiece chessPieceInUse, float vectorX, float vectorY, int xEndPosition, int yEndPosition) throws InvalidMoveException {
        service.move(chessPieceInUse, vectorX, vectorY, xEndPosition, yEndPosition);
    }
}
