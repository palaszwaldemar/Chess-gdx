// TODO: 24.11.2023 klasa do usunięcia. Stworzona na potrzebę testowania roszady.
package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chess.exceptions.InvalidMoveException;

public class ButtonToCastlingTest extends Actor {
    private final Texture image;
    private final Array<ChessPieceActor> myChessPieceActors = new Array<>();
    private final Controller controller;

    public ButtonToCastlingTest(Array<Actor> chessPieceActors, Controller controller) {
        image = new Texture(Gdx.files.internal("castlingTest/castlingTest.png"));
        for (Actor chessPieceActor : chessPieceActors) {
            if (chessPieceActor instanceof ChessPieceActor) {
                myChessPieceActors.add((ChessPieceActor) chessPieceActor);
            }
        }
        this.controller = controller;
        setBounds(10, 10, 100, 50);
        addListener(new MoveChessPieces());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY());
    }

    private class MoveChessPieces extends DragListener {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            try {
                moveChessPiecesToCastlingTest();
                System.out.println("tryb testu roszady");
            } catch (InvalidMoveException e) {
                System.out.println("error moves");
            }
        }

        private void moveChessPiecesToCastlingTest() throws InvalidMoveException {
            //WHITE
            controller.move(myChessPieceActors.get(0).getChessPiece(), 0, 3);
            myChessPieceActors.get(0).setPosition(0, 300);

            controller.move(myChessPieceActors.get(1).getChessPiece(), 1, 3);
            myChessPieceActors.get(1).setPosition(100, 300);

            controller.move(myChessPieceActors.get(2).getChessPiece(), 2, 3);
            myChessPieceActors.get(2).setPosition(200, 300);

            controller.move(myChessPieceActors.get(3).getChessPiece(), 3, 3);
            myChessPieceActors.get(3).setPosition(300, 300);

            controller.move(myChessPieceActors.get(4).getChessPiece(), 4, 3);
            myChessPieceActors.get(4).setPosition(400, 300);

            controller.move(myChessPieceActors.get(5).getChessPiece(), 5, 3);
            myChessPieceActors.get(5).setPosition(500, 300);

            controller.move(myChessPieceActors.get(6).getChessPiece(), 6, 3);
            myChessPieceActors.get(6).setPosition(600, 300);

            controller.move(myChessPieceActors.get(7).getChessPiece(), 7, 3);
            myChessPieceActors.get(7).setPosition(700, 300);

            controller.move(myChessPieceActors.get(9).getChessPiece(), 0, 2);
            myChessPieceActors.get(9).setPosition(0, 200);

            controller.move(myChessPieceActors.get(10).getChessPiece(), 4, 2);
            myChessPieceActors.get(10).setPosition(400, 200);

            controller.move(myChessPieceActors.get(11).getChessPiece(), 1, 2);
            myChessPieceActors.get(11).setPosition(100, 200);

            controller.move(myChessPieceActors.get(13).getChessPiece(), 3, 2);
            myChessPieceActors.get(13).setPosition(300, 200);

            controller.move(myChessPieceActors.get(14).getChessPiece(), 7, 2);
            myChessPieceActors.get(14).setPosition(700, 200);


            //BLACK
            controller.move(myChessPieceActors.get(16).getChessPiece(), 0, 4);
            myChessPieceActors.get(16).setPosition(0, 400);

            controller.move(myChessPieceActors.get(17).getChessPiece(), 1, 4);
            myChessPieceActors.get(17).setPosition(100, 400);

            controller.move(myChessPieceActors.get(18).getChessPiece(), 2, 4);
            myChessPieceActors.get(18).setPosition(200, 400);

            controller.move(myChessPieceActors.get(19).getChessPiece(), 3, 4);
            myChessPieceActors.get(19).setPosition(300, 400);

            controller.move(myChessPieceActors.get(20).getChessPiece(), 4, 4);
            myChessPieceActors.get(20).setPosition(400, 400);

            controller.move(myChessPieceActors.get(21).getChessPiece(), 5, 4);
            myChessPieceActors.get(21).setPosition(500, 400);

            controller.move(myChessPieceActors.get(22).getChessPiece(), 6, 4);
            myChessPieceActors.get(22).setPosition(600, 400);

            controller.move(myChessPieceActors.get(23).getChessPiece(), 7, 4);
            myChessPieceActors.get(23).setPosition(700, 400);

            controller.move(myChessPieceActors.get(25).getChessPiece(), 0, 5);
            myChessPieceActors.get(25).setPosition(0, 500);

            controller.move(myChessPieceActors.get(26).getChessPiece(), 4, 5);
            myChessPieceActors.get(26).setPosition(400, 500);

            controller.move(myChessPieceActors.get(27).getChessPiece(), 1, 5);
            myChessPieceActors.get(27).setPosition(100, 500);

            controller.move(myChessPieceActors.get(29).getChessPiece(), 3, 5);
            myChessPieceActors.get(29).setPosition(300, 500);

            controller.move(myChessPieceActors.get(30).getChessPiece(), 7, 5);
            myChessPieceActors.get(30).setPosition(700, 500);
        }
    }
}