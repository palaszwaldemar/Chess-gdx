package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.CordsVector;
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

    void move(ChessPiece chessPieceInUse, CordsVector endCordsVector) throws InvalidMoveException {
        service.move(chessPieceInUse, endCordsVector);
        //chessboardGroup.removeActorAt(endCordsVector);
        // TODO: 17.10.2023 moveRaport tutaj wykonuje działania
    }

    public void removeActor(ChessPiece chessPieceToRemove) {
        Array<Actor> actors = chessboardGroup.getChildren();
        Array<ChessPieceActor> children = new Array<>();

        for (Actor actor : actors) {
            if (actor instanceof ChessPieceActor) {
                children.add((ChessPieceActor) actor);
            }
        }
        for (ChessPieceActor child : children) {
            if (child.getChessPiece().equals(chessPieceToRemove)) {
                chessboardGroup.removeActor(child);
            }
        }
    }

    public void addChessPieceActor(ChessPiece chessPiece) {// TODO: 11.10.2023 napisane na szybko
        chessboardGroup.addActor(new ChessPieceActor(chessPiece, this));
    }
}
