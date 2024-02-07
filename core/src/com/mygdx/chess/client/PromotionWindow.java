package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.chess.server.MoveReport;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class PromotionWindow extends Actor {
    private Texture texture;
    private final MoveReport moveReport;
    private final Controller controller;
    private final Stage stage;

    public PromotionWindow(ChessPiece chessPieceInUse, MoveReport moveReport, Controller controller) {
        setBounds(GuiParams.PROMOTION_WINDOW_X_POSITION, GuiParams.PROMOTION_WINDOW_Y_POSITION,
            GuiParams.PROMOTION_WINDOW_WIDTH, GuiParams.PROMOTION_WINDOW_HEIGHT);
        setTexture(chessPieceInUse);
        this.moveReport = moveReport;
        this.controller = controller;
        stage = controller.getStage();
        addListener(new PromotionWindowListener(this));
    }

    private void setTexture(ChessPiece chessPieceInUse) {
        String color = chessPieceInUse.getColor().toString();
        texture = new Texture(Gdx.files.internal("promotion/" + color + "_promotion.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    private class PromotionWindowListener extends ClickListener {
        private final PromotionWindow promotionWindow;

        private PromotionWindowListener(PromotionWindow promotionWindow) {
            this.promotionWindow = promotionWindow;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            controller.replaceChessPieceActor(moveReport);
            controller.setActorFocus(promotionWindow, false);
            promotionWindow.clearListeners();
            stage.getRoot().removeActor(promotionWindow);
            super.clicked(event, x, y);
        }
    }
}
