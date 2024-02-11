package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.chess.server.ChessPiece;

import java.util.ArrayList;
import java.util.List;

class ChessPieceGroup extends Group {
    private final Texture boardImage;
    private Controller controller;

    ChessPieceGroup() {
        setBounds(GuiParams.CHESSBOARD_X_POSITION, GuiParams.CHESSBOARD_Y_POSITION, GuiParams.CHESSBOARD_WIDTH,
            GuiParams.CHESSBOARD_HEIGHT);
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
    }

    void setController(Controller controller) {
        this.controller = controller;
    }

    void createActors(List<ChessPiece> chessPieces) {
        for (ChessPiece chessPiece : chessPieces) {
            addActor(new ChessPieceActor(chessPiece, controller));
        }
    }

    List<ChessPieceActor> getChessPieceActors() {
        List<ChessPieceActor> chessPieceActors = new ArrayList<>();
        for (Actor actor : getChildren()) {
            chessPieceActors.add((ChessPieceActor) actor);
        }
        return chessPieceActors;
    }

    void removeActor(ChessPiece chessPieceToRemove) {
        List<ChessPieceActor> chessPieceActors = getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) { // todo stream
            if (chessPieceActor.getChessPiece().equals(chessPieceToRemove)) {
                removeActor(chessPieceActor);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, getX(), getY());
        super.draw(batch, parentAlpha);
    }
}
