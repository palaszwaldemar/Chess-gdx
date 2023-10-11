package com.mygdx.chess.client;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.EndCordsVector;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class Controller {
    private final ChessBoardService service = new ChessBoardService(this);
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

    public void removeActor(EndCordsVector endCordsVector) {
        Array<Actor> actors = chessboardGroup.getChildren();
        Array<ChessPieceActor> children = new Array<>();

        for (Actor actor : actors) {
            if (actor instanceof ChessPieceActor) {
                children.add((ChessPieceActor) actor);
            }
        }
        for (ChessPieceActor child : children) {
            if (child.getChessPiece().getX() == endCordsVector.x && child.getChessPiece().getY() == endCordsVector.y) {
                chessboardGroup.removeActor(child);
            }
        }
    }
}
