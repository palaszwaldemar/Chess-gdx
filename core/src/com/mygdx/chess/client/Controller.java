package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.chess.server.*;

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
        ChessGameDto chessGameDto = service.getChessGame();
        PlayerGroup friendPlayer = getPlayerByColor(chessGameDto.activeColor());
        PlayerGroup enemyPlayer = getPlayerByColor(chessGameDto.activeColor().getSecondColor());
        friendPlayer.createActors(chessGameDto.whiteChessPieces());
        enemyPlayer.createActors(chessGameDto.blackChessPieces());
        enemyPlayer.setTouchable(Touchable.disabled);
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
        switchPlayer();
        return moveReport.isValid();
    }

    private void switchPlayer() {
        for (PlayerGroup player : players) {
            if (player.getChessPieceColor() == moveReport.getNextColor()) {
                player.setTouchable(Touchable.enabled);
            } else {
                player.setTouchable(Touchable.disabled);
            }
        }
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

    void removeActorFromStage(Actor actor) {
        stage.getRoot().removeActor(actor);
    }

    private PlayerGroup getPlayerByColor(ChessPieceColor color) {
        for (PlayerGroup player : players) {
            if (player.getChessPieceColor() == color) {
                return player;
            }
        }
        throw new IllegalStateException("no player of matching color");
    }

    private PlayerGroup lastPlayer() {
        return getPlayerByColor(moveReport.getActiveColor());
    }

    private PlayerGroup lastPlayerEnemy() {
        return getPlayerByColor(moveReport.getNextColor());
    }
}
