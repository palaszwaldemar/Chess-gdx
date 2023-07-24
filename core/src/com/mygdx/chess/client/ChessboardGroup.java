package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.ChessPiece;

import java.util.ArrayList;
import java.util.List;

class ChessboardGroup extends Group {
    private final Texture boardImage;
    private final ChessBoardService service = new ChessBoardService();

    ChessboardGroup() {
        boardImage = new Texture(Gdx.files.internal("chessboard/chessboard.png"));
        addAllActors();
    }

    private void addAllActors() {
        List<ChessPiece> chessPieces = new ArrayList<>(service.getChessPieces());
        for (ChessPiece chessPiece : chessPieces) {
            addActor(new ChessPieceActor(chessPiece, chessPiece.getX(), chessPiece.getY())); // CHECK: 24.07.2023 czy tak może zostać? Że mam dostęp do getX i getY?
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardImage, getX(), getY());
        super.draw(batch, parentAlpha);
    }
}
