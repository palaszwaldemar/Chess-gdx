package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;
import com.mygdx.chess.server.MoveDto;

import static com.mygdx.chess.client.GuiParams.*;
import static com.mygdx.chess.server.ChessPieceType.*;

class PromotionWindow extends Actor {
    private Texture texture;
    private final Controller controller;
    private final MoveDto move;

    PromotionWindow(MoveDto move, Controller controller) {
        setBounds(PROMOTION_WINDOW_X_POSITION, PROMOTION_WINDOW_Y_POSITION, PROMOTION_WINDOW_WIDTH,
            PROMOTION_WINDOW_HEIGHT);
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
            controller.removeActorFromStage(PromotionWindow.this);
            controller.continueMove(move.withType(type((int) x)));
            super.clicked(event, x, y);
        }

        private ChessPieceType type(int pixelX) {
            int index = pixelX / CHESS_PIECE_SIZE;
            ChessPieceType[] pieces = {QUEEN, ROOK, RUNNER, KNIGHT};
            if (index >= pieces.length) {
                throw new IllegalStateException("no selected figure");
            }
            return pieces[index];
        }
    }
}
