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

    boolean move(MoveDto move) {
        if (service.isPromotion(move)) {
            showPromotionWindow(move);
            return true;
        } else {
            return continueMove(move);
        }
    }

    private void showPromotionWindow(MoveDto move) {
        PromotionWindow promotionWindow = new PromotionWindow(move, this);
        stage.addActor(promotionWindow);
        lockChessboard(true);
    }

    boolean continueMove(MoveDto move) {
        MoveReport moveReport = service.move(move);
        if (!moveReport.isValid()) {
            return false;
        }
        chessPieceGroup.removeActor(moveReport.getChessPieceToRemove());
        castling(moveReport);
        promotion(moveReport);
        return moveReport.isValid();
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

    void lockChessboard(boolean enable) {
        if (enable) {
            chessPieceGroup.setTouchable(Touchable.disabled);
            return;
        }
        chessPieceGroup.setTouchable(Touchable.enabled);
    }

    private void promotion(MoveReport moveReport) {
        if (!moveReport.wasPromotion()) {
            return;
        }
        lockChessboard(false);
        chessPieceGroup.removeActor(moveReport.getPromotionPawnToRemove());
        chessPieceGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }

    void removeActor(Actor actor) {
        stage.getRoot().removeActor(actor);
    }
}
