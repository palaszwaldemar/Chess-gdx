package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class PromotionWindow extends Actor {
    private Texture texture;

    public PromotionWindow(ChessPiece chessPieceInUse) {
        setBounds(GuiParams.PROMOTION_WINDOW_X_POSITION, GuiParams.PROMOTION_WINDOW_Y_POSITION,
            GuiParams.PROMOTION_WINDOW_WIDTH, GuiParams.PROMOTION_WINDOW_HEIGHT);
        setTexture(chessPieceInUse);
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
}
