package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.chess.server.ChessPiece;

class ChessPieceActor extends Actor {
    private final Texture image;
    private final ChessPiece chessPiece;

    ChessPieceActor(ChessPiece chessPiece) {
        image = new Texture(Gdx.files.internal(chessPiece.getStringImage()));
        this.chessPiece = chessPiece;
        setBounds(Cords.xToPixels(chessPiece.getX()), Cords.yToPixels(chessPiece.getY()),
                GuiParams.CHESS_PIECE_WIDTH, GuiParams.CHESS_PIECE_HEIGHT);
        addListener(new DragChessPieceListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY());
    }

    private class DragChessPieceListener extends DragListener {
        private final Vector2 startTouchPosition = new Vector2();
        private final Vector2 startPosition = new Vector2();

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            startTouchPosition.set(x, y);
            startPosition.set(getX(), getY());
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            Vector2 vector2 = getParent().stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
            setPosition(vector2.x - startTouchPosition.x, vector2.y - startTouchPosition.y);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Vector2 vector2 = getParent().stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
            boolean isWithinParentsBounds = vector2.x >= 0 && vector2.x <= getParent().getWidth() &&
                    vector2.y >= 0 && vector2.y <= getParent().getHeight();

            if (isWithinParentsBounds) {
                setPosition(((int) (vector2.x / GuiParams.CHESS_PIECE_WIDTH)) * GuiParams.CHESS_PIECE_WIDTH,
                        ((int) (vector2.y / GuiParams.CHESS_PIECE_HEIGHT)) * GuiParams.CHESS_PIECE_HEIGHT);
                chessPiece.setGridPosition(Cords.xToCords((int) getX()), Cords.yToCords((int) getY()));
            } else {
                setPosition(startPosition.x, startPosition.y);
            }
            super.touchUp(event, x, y, pointer, button);
        }
    }
}
