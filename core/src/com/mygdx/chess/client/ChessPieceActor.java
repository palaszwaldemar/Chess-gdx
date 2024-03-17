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
            startTouchPosition.set(x, y);
            startPosition.set(getX(), getY());
            setOnTopLayer();// CHECK : 11.03.2024 czy tak może być?
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
            /*// CHECK : 15.03.2024 skoro sprawdzam tutaj mouseDropPosition < 0 to czy od razu nie mógłbym
            // sprawdzić > 0 ?? Tylko wtedy metoda onBoards() z klasy MoveValidator za bardzo nie ma sensu
            *//*if (mouseDropPosition.x < 0 || mouseDropPosition.y < 0) {
                setOnStartPosition();
                super.touchUp(event, x, y, pointer, button);// CHECK : 15.03.2024 dwa razy powtórzone super. Czy ok?
                return;
            }*/
            int xPixels = calculateSnappedPosition(mouseDropPosition.x, GuiParams.CHESS_PIECE_WIDTH);
            int yPixels = calculateSnappedPosition(mouseDropPosition.y, GuiParams.CHESS_PIECE_HEIGHT);
            int xCords = Cords.xToCords(xPixels);
            int yCords = Cords.yToCords(yPixels);
            boolean valid = controller.move(MoveDto.create(chessPiece, xCords, yCords));
            if (valid) {
                setPosition(xPixels, yPixels);
            } else {
                setOnStartPosition();
            }
            super.touchUp(event, x, y, pointer, button);
        }

        private void setOnTopLayer() {
            toFront();
            getParent().toFront();
        }

        private void setOnStartPosition() {
            setPosition(startPosition.x, startPosition.y);
        }

        private int calculateSnappedPosition(float position, int gridSize) {
            return (int) Math.floor(position / gridSize) * gridSize;
        }
    }
}