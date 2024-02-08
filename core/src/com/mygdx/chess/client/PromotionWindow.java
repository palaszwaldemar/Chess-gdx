package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.MoveReport;

class PromotionWindow extends Actor {
    private Texture texture;
    private final MoveReport moveReport;
    private final Controller controller;

    PromotionWindow(MoveReport moveReport, Controller controller) {
        setBounds(GuiParams.PROMOTION_WINDOW_X_POSITION, GuiParams.PROMOTION_WINDOW_Y_POSITION,
            GuiParams.PROMOTION_WINDOW_WIDTH, GuiParams.PROMOTION_WINDOW_HEIGHT);
        setDisplay(moveReport.getChessPieceInUse().getColor());
        this.moveReport = moveReport;
        this.controller = controller;
        addListener(new PromotionWindowListener());
    }

    private void setDisplay(ChessPieceColor color) {
        texture = new Texture(Gdx.files.internal("promotion/" + color.toString() + "_promotion.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    private class PromotionWindowListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ChessPieceType type = ChessPieceType.QUEEN; //todo
            controller.promotionChessPieceSelected(type, moveReport);
            controller.lockChessboard(false);
            controller.removeActor(PromotionWindow.this);
            super.clicked(event, x, y);
        }
    }
}
