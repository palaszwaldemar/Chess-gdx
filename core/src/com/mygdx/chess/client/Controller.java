package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.MoveReport;
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

    void move(ChessPiece chessPieceInUse, int x, int y) throws InvalidMoveException {
        MoveReport moveReport = service.move(chessPieceInUse, x, y);
        removeActor(moveReport.getChessPieceToRemove());
        castling(moveReport);
        if (moveReport.wasPromotion()) {
            replaceChessPieceActor(moveReport);
        }
    }

    private void removeActor(ChessPiece chessPieceToRemove) {
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

    // CHECK : 05.12.2023 sprawdzić na następnej lekcji
    private void castling(MoveReport moveReport) {
        Array<Actor> actors = chessboardGroup.getChildren();
        Array<ChessPieceActor> children = new Array<>();
        for (Actor actor : actors) {
            if (actor instanceof ChessPieceActor) {
                children.add((ChessPieceActor) actor);
            }
        }
        for (ChessPieceActor child : children) {
            if (child.getChessPiece().equals(moveReport.getRookToMove())) {
                child.setPosition(moveReport.getNewXRook(), moveReport.getNewYRook());
            }
        }
    }

    private void replaceChessPieceActor(MoveReport moveReport) {
        removeActor(moveReport.getPromotionPawnToRemove());
        chessboardGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }
}
