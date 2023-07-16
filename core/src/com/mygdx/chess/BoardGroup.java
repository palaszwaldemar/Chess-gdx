package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BoardGroup extends Group {
    private final Texture boardImage;

    public BoardGroup() {
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, 100, 100);
    }
}
