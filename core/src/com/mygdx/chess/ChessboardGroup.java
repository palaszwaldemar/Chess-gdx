package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

public class ChessboardGroup extends Group {
    private final Texture boardImage;
    private final PiecesFactory piecesFactory = new PiecesFactory();

    public ChessboardGroup() {
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
        addAllActors();
    }

    private void addAllActors() {
        List<ChessPieceActor> chessPiecesActors = new ArrayList<>(piecesFactory.getChessPieces());
        for (ChessPieceActor chessPieceActor : chessPiecesActors) {
            addActor(chessPieceActor);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, getX(), getY());
        super.draw(batch, parentAlpha);
    }
}
