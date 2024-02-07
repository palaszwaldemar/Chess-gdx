package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
            PromotionWindow promotionWindow = new PromotionWindow(chessPieceInUse);
            stage.addActor(promotionWindow);


            // TODO: 07.02.2024 wyłącz nasłuch dla wszystkich aktorów oprócz promotionWindow
            for (Actor actor : stage.getActors()) {
                if (actor != promotionWindow) {
                    actor.setTouchable(Touchable.disabled);
                }
            }

            promotionWindow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: 07.02.2024 włącz nasłuch dla wszystkich aktorów
                    replaceChessPieceActor(moveReport);
                    for (Actor actor : stage.getActors()) {
                            actor.setTouchable(Touchable.enabled);
                    }
                    super.clicked(event, x, y);
                }
            });
        }
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
    private void replaceChessPieceActor(MoveReport moveReport) {
        chessPieceGroup.removeActorBy(moveReport.getPromotionPawnToRemove());
        chessPieceGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }

    public ChessPieceColor whichColorTurn() {
        return service.whichColorTurn();
    }
}
