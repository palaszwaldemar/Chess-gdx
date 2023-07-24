package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.chess.server.ChessPiece;

class ChessPieceActor extends Actor {
    private final Texture image;

    ChessPieceActor(ChessPiece chessPiece, int x, int y) {
        image = new Texture(Gdx.files.internal(chessPiece.getStringImage()));
        setGridPosition(x, y);
    }

    void setGridPosition(int x, int y) {
        setX(Cords.xToPixels(x));
        setY(Cords.yToPixels(y));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY());
    }
}
