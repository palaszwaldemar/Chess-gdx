package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.MoveReport;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessPieceGroup chessPieceGroup;
    private final Stage stage;

    public Controller(Stage stage, ChessPieceGroup chessPieceGroup) {
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
        if (moveReport.wasPromotion()) {
            PromotionWindow promotionWindow = new PromotionWindow(chessPieceInUse, moveReport, this);
            stage.addActor(promotionWindow);
            setActorFocus(promotionWindow, true);
        }
        return moveReport;
    }

    void setActorFocus(Actor focusedActor, boolean enable) {
        if (enable) {
            for (Actor actor : stage.getActors()) {
                if (actor != focusedActor) {
                    actor.setTouchable(Touchable.disabled);
                }
            }
            return;
        }
        for (Actor actor : stage.getActors()) {
            actor.setTouchable(Touchable.enabled);
        }
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

    void replaceChessPieceActor(MoveReport moveReport) {
        chessPieceGroup.removeActorBy(moveReport.getPromotionPawnToRemove());
        chessPieceGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }

    public ChessPieceColor whichColorTurn() {
        return service.whichColorTurn();
    }

    public Stage getStage() {
        return stage;
    }
}
