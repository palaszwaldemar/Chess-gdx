package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.CordsVector;
import com.mygdx.chess.server.MoveReport;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class Controller { // CHECK : 19.10.2023 klasa Controller używana tylko na froncie
    private final ChessBoardService service = new ChessBoardService();
    private final ChessboardGroup chessboardGroup;

    public Controller(ChessboardGroup chessboardGroup) {
        this.chessboardGroup = chessboardGroup;
    }

    void startGame() {
        chessboardGroup.createActors(service.getChessPieces());
    }

    void move(ChessPiece chessPieceInUse, CordsVector endCordsVector) throws InvalidMoveException {
        MoveReport moveReport = service.move(chessPieceInUse, endCordsVector);
        removeActor(moveReport.getChessPieceToRemove());
        if (moveReport.getPromotionPawnToRemove() != null) {// CHECK : 19.10.2023 czy tak może byc?
            replaceChessPieceActor(moveReport);
        }
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

    public void replaceChessPieceActor(MoveReport moveReport) {
        removeActor(moveReport.getPromotionPawnToRemove());
        chessboardGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }
}
