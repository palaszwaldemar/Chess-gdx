package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.chess.server.ChessPiece;

import java.util.ArrayList;
import java.util.List;

class ChessboardGroup extends Group {
    private final Texture boardImage;
    private final Controller controller = new Controller();

    ChessboardGroup() {
        setBounds(GuiParams.CHESSBOARD_X_POSITION, GuiParams.CHESSBOARD_Y_POSITION, GuiParams.CHESSBOARD_WIDTH, GuiParams.CHESSBOARD_HEIGHT);
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
        addAllActors();
    }

    private void addAllActors() {
        List<ChessPiece> chessPieces = new ArrayList<>(controller.listOfPieces());
        for (ChessPiece chessPiece : chessPieces) {
            addActor(new ChessPieceActor(chessPiece));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, getX(), getY());
        super.draw(batch, parentAlpha);
    }
}
