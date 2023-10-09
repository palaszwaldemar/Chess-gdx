package com.mygdx.chess.client;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.EndCordsVector;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessboardGroup chessboardGroup;

    public Controller(ChessboardGroup chessboardGroup) {
        this.chessboardGroup = chessboardGroup;
    }

    void startGame() {
        chessboardGroup.createActors(service.getChessPieces());
    }

    void move(ChessPiece chessPieceInUse, EndCordsVector endCordsVector, Vector2 mouseDropPosition) throws InvalidMoveException {
        service.move(chessPieceInUse, endCordsVector, mouseDropPosition);
    }
}
