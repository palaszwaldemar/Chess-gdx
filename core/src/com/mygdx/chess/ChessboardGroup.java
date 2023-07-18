package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ChessboardGroup extends Group {
    private final Texture boardImage;

    public ChessboardGroup() {
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
        ChessPieceActor chessPieceActor = new ChessPieceActor(new ChessPiece());
        ChessPieceActor chessPieceActor1 = new ChessPieceActor(new ChessPiece());
        chessPieceActor.setGridPosition(1, 2);
        chessPieceActor1.setGridPosition(2, 2);
        addActor(chessPieceActor);
        addActor(chessPieceActor1);
        System.out.println(chessPieceActor); // TODO: 18.07.2023 to remove
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, GuiParams.CHESSBOARD_POSITION, GuiParams.CHESSBOARD_POSITION);
        super.draw(batch, parentAlpha);
    }
}
