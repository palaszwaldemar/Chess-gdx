package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.chess.server.MoveDto;
import com.mygdx.chess.server.MoveReport;
import com.mygdx.chess.server.ServerFacade;

import java.util.List;

class Controller {
    private final ServerFacade service = new ServerFacade();
    private final List<PlayerGroup> players;
    private final Stage stage;
    private MoveReport moveReport;

    Controller(Stage stage, List<PlayerGroup> players) {
        this.players = players;
        this.stage = stage;
    }

    void startGame() {
        for (PlayerGroup player : players) {
            player.createActors(service.getChessPieces(player.getChessPieceColor()));
        }
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

        this.moveReport = moveReport;
        lastPlayerEnemy().removeActor(moveReport.getChessPieceToRemove());
        castling(moveReport);
        promotion(moveReport);
        return moveReport.isValid();
    }

    private void castling(MoveReport moveReport) {
        List<ChessPieceActor> chessPieceActors = lastPlayer().getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(moveReport.getRookToMove())) {
                float newXRook = Cords.xToPixels(moveReport.getNewXRook());
                float newYRook = Cords.yToPixels(moveReport.getNewYRook());
                chessPieceActor.setPosition(newXRook, newYRook);
            }
        }
    }

    void lockChessboard(boolean enable) {
        for (PlayerGroup player : players) {
            player.setTouchable(enable ? Touchable.disabled : Touchable.enabled);
        }
    }

    private void promotion(MoveReport moveReport) {
        if (!moveReport.wasPromotion()) {
            return;
        }
        lockChessboard(false);
        lastPlayer().removeActor(moveReport.getPromotionPawnToRemove());
        lastPlayer().addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }

    void removeActor(Actor actor) {
        stage.getRoot().removeActor(actor);
    }

    private PlayerGroup lastPlayer() {
        for (PlayerGroup player : players) {
            if(player.getChessPieceColor() == moveReport.actualColor()) {
                return player;
            }
        }
        throw new IllegalStateException("no player of matching color");
    }

    private PlayerGroup lastPlayerEnemy() {
        for (PlayerGroup player : players) {
            if(player.getChessPieceColor() != moveReport.actualColor()) {
                return player;
            }
        }
        throw new IllegalStateException("no player of matching color");
    }
}
