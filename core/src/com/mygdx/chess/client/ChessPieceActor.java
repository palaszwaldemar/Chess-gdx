package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.chess.server.ChessPiece;
import com.mygdx.chess.server.MoveDto;

class ChessPieceActor extends Actor {
    private final ChessPiece chessPiece;
    private final Controller controller;
    private final Texture image;

    ChessPieceActor(ChessPiece chessPiece, Controller controller) {
        this.chessPiece = chessPiece;
        this.controller = controller;
        image =
            new Texture(Gdx.files.internal("chess_pieces/" + chessPiece.getType() + chessPiece.getColor() + ".png"));
        setBounds(Cords.xToPixels(chessPiece.getX()), Cords.yToPixels(chessPiece.getY()), GuiParams.CHESS_PIECE_WIDTH,
            GuiParams.CHESS_PIECE_HEIGHT);
        addListener(new DragChessPieceListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY());
    }

    ChessPiece getChessPiece() {
        return chessPiece;
    }

    private class DragChessPieceListener extends DragListener {
        private final Vector2 startTouchPosition = new Vector2();
        private final Vector2 startPosition = new Vector2();

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            /*if (controller.whichColorTurn() != chessPiece.getColor()) {
                return false;
            }*/
            startTouchPosition.set(x, y);
            startPosition.set(getX(), getY());
            toFront();
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            Vector2 vector2 = getParent().stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
            setPosition(vector2.x - startTouchPosition.x, vector2.y - startTouchPosition.y);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Vector2 mouseDropPosition =
                getParent().stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
            int xPixels = ((int) (mouseDropPosition.x / GuiParams.CHESS_PIECE_WIDTH)) * GuiParams.CHESS_PIECE_WIDTH;
            int yPixels = ((int) (mouseDropPosition.y / GuiParams.CHESS_PIECE_HEIGHT)) * GuiParams.CHESS_PIECE_HEIGHT;
            int xCords = Cords.xToCords(xPixels);
            int yCords = Cords.yToCords(yPixels);

            boolean valid = controller.move(MoveDto.create(chessPiece,xCords,yCords));

            if (valid) {
                setPosition(xPixels, yPixels);
            } else {
                setPosition(startPosition.x, startPosition.y);
            }
            super.touchUp(event, x, y, pointer, button);
        }
    }
}