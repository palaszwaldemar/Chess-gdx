package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class ChessboardGroup extends Group {
    private final Texture boardImage;
    private Controller controller;

    ChessboardGroup() {
        setBounds(GuiParams.CHESSBOARD_X_POSITION, GuiParams.CHESSBOARD_Y_POSITION, GuiParams.CHESSBOARD_WIDTH,
            GuiParams.CHESSBOARD_HEIGHT);
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    void createActors(List<ChessPiece> chessPieces) {
        for (ChessPiece chessPiece : chessPieces) {
            addActor(new ChessPieceActor(chessPiece, controller));
        }
    }

    SnapshotArray<ChessPieceActor> getChessPieceActors() { // CHECK : 19.01.2024 czy może być tak?
        SnapshotArray<ChessPieceActor> chessPieceActors = new SnapshotArray<>();
        for (Actor actor : getChildren())
            if (actor instanceof ChessPieceActor) {
                chessPieceActors.add((ChessPieceActor) actor);
            }
        return chessPieceActors;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, getX(), getY());
        super.draw(batch, parentAlpha);
    }
}
