package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.chess.server.ChessPiece;

import java.util.List;

public class ChessboardGroup extends Group {
    private final Texture boardImage;
    private Controller controller;

    ChessboardGroup() {
        setBounds(GuiParams.CHESSBOARD_X_POSITION, GuiParams.CHESSBOARD_Y_POSITION, GuiParams.CHESSBOARD_WIDTH, GuiParams.CHESSBOARD_HEIGHT);
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

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, getX(), getY());
        super.draw(batch, parentAlpha);
    }
}
