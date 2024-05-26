package com.mygdx.chess.client;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.chess.server.ChessPiece;
import com.mygdx.chess.server.ChessPieceColor;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.chess.client.GuiParams.CHESSBOARD_POSITION;

class PlayerGroup extends Group {
    private Controller controller;
    private final ChessPieceColor color;

    PlayerGroup(ChessPieceColor color) {
        this.color = color;
        setPosition(CHESSBOARD_POSITION, CHESSBOARD_POSITION);
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

    ChessPieceColor getChessPieceColor() {
        return color;
    }

    void removeActor(ChessPiece chessPieceToRemove) {
        List<ChessPieceActor> chessPieceActors = getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(chessPieceToRemove)) {
                removeActor(chessPieceActor);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
