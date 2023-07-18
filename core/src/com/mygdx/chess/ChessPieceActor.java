package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ChessPieceActor extends Actor {
    private final ChessPiece chessPiece;
    private final Texture image;

    public ChessPieceActor(ChessPiece chessPiece, ChessPieceType type, ChessPieceColor color, int x, int y) {
        this.chessPiece = chessPiece;
        image = new Texture(Gdx.files.internal("chess_pieces/" + type.getName() + color.getName() + ".png"));
        setGridPosition(x, y);
    }

    public void setGridPosition(int x, int y) {
        setX(Cords.xToPixels(x));
        setY(Cords.yToPixels(y));
        chessPiece.setX(x);
        chessPiece.setY(y);
        System.out.println(chessPiece); // TODO: 18.07.2023 remove
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY());
    }

    @Override
    public String toString() {
        return "ChessPieceActor{" +
                "chessPiece=" + chessPiece +
                ", image=" + image +
                '}';
    }
}
