package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.chess.server.*;

class PromotionWindow extends Actor {
    private Texture texture;
    private final Controller controller;
    private final MoveDto move;

    PromotionWindow(MoveDto move, Controller controller) {
        setBounds(GuiParams.PROMOTION_WINDOW_X_POSITION, GuiParams.PROMOTION_WINDOW_Y_POSITION,
            GuiParams.PROMOTION_WINDOW_WIDTH, GuiParams.PROMOTION_WINDOW_HEIGHT);
        setDisplay(move.inUse().getColor());
        this.controller = controller;
        this.move = move;
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
            ChessPieceType type = ChessPieceType.RUNNER; //todo
            controller.removeActor(PromotionWindow.this);
            controller.continueMove(move.withType(type));
            super.clicked(event, x, y);
        }
    }
}
