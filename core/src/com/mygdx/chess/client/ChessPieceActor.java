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

import static com.mygdx.chess.client.GuiParams.CHESS_PIECE_SIZE;

class ChessPieceActor extends Actor {
    private final ChessPiece chessPiece;
    private final Controller controller;
    private final Texture image;

    ChessPieceActor(ChessPiece chessPiece, Controller controller) {
        this.chessPiece = chessPiece;
        this.controller = controller;
        image =
            new Texture(Gdx.files.internal("chess_pieces/" + chessPiece.getType() + chessPiece.getColor() + ".png"));
        setBounds(Cords.xToPixels(chessPiece.getX()), Cords.yToPixels(chessPiece.getY()), CHESS_PIECE_SIZE,
            CHESS_PIECE_SIZE);
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
            startTouchPosition.set(x, y);
            startPosition.set(getX(), getY());
            setOnTopLayer();
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            Vector2 vector2 = getParent().stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
            setPosition(vector2.x - startTouchPosition.x, vector2.y - startTouchPosition.y);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            Vector2 mouseDropPosition =
                getParent().stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
            int xPixels = calculateSnappedPosition(mouseDropPosition.x);
            int yPixels = calculateSnappedPosition(mouseDropPosition.y);
            int xCords = Cords.xToCords(xPixels);
            int yCords = Cords.yToCords(yPixels);
            if (!cordsValid(xCords, yCords)) {
                setOnStartPosition();
                return;
            }
            boolean valid = controller.move(MoveDto.create(chessPiece, xCords, yCords));
            if (!valid) {
                setOnStartPosition();
                return;
            }
            setPosition(xPixels, yPixels);
        }

        private void setOnTopLayer() {
            toFront();
            getParent().toFront();
        }

        private boolean cordsValid(int xCords, int yCords) {
            return xCords >= 0 && xCords <= 7 && yCords >= 0 && yCords <= 7;
        }

        private void setOnStartPosition() {
            setPosition(startPosition.x, startPosition.y);
        }

        private int calculateSnappedPosition(float position) {
            return (int) Math.floor(position / CHESS_PIECE_SIZE) * CHESS_PIECE_SIZE;
        }
    }
}