package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.chess.server.*;

import java.util.List;

class Controller {
    private final ServerFacade service = new ServerFacade();
    private final ChessPieceGroup chessPieceGroup;
    private final Stage stage;

    Controller(Stage stage, ChessPieceGroup chessPieceGroup) {
        this.chessPieceGroup = chessPieceGroup;
        this.stage = stage;
    }

    void startGame() {
        chessPieceGroup.createActors(service.getChessPieces());
    }

    MoveReport move(ChessPiece chessPieceInUse, int x, int y) {
        MoveReport moveReport = service.move(chessPieceInUse, x, y);
        if (!moveReport.isValid()) {
            return moveReport;
        }
        chessPieceGroup.removeActorBy(moveReport.getChessPieceToRemove());
        castling(moveReport);
        promotion(moveReport);
        return moveReport;
    }

    private void castling(MoveReport moveReport) {
        List<ChessPieceActor> chessPieceActors = chessPieceGroup.getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(moveReport.getRookToMove())) {
                float newXRook = Cords.xToPixels(moveReport.getNewXRook());
                float newYRook = Cords.yToPixels(moveReport.getNewYRook());
                chessPieceActor.setPosition(newXRook, newYRook);
            }
        }
    }

    private void promotion(MoveReport moveReport) {
        if (moveReport.wasPromotion()) {
            PromotionWindow promotionWindow = new PromotionWindow(moveReport, this);
            stage.addActor(promotionWindow);
            lockChessboard(true);
        }
    }

    void lockChessboard(boolean enable) {
        if (enable) {
            chessPieceGroup.setTouchable(Touchable.disabled);
            return;
        }
        chessPieceGroup.setTouchable(Touchable.enabled);
    }

    void promotionChessPieceSelected(ChessPieceType type, MoveReport moveReport) {
        replaceChessPieceActor(moveReport);
        lockChessboard(false);
    }

    private void replaceChessPieceActor(MoveReport moveReport) {
        chessPieceGroup.removeActorBy(moveReport.getPromotionPawnToRemove());
        chessPieceGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }

    void removeActor(Actor actor) {
        stage.getRoot().removeActor(actor);
    }

    ChessPieceColor whichColorTurn() {
        return service.whichColorTurn();
    }
}
