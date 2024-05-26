package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.chess.server.MoveReport;

import static com.mygdx.chess.client.GuiParams.*;

public class EndWindow extends Actor {
    private final Controller controller;
    private final Texture texture;

    EndWindow(MoveReport moveReport, Controller controller) {
        setBounds(END_WINDOW_X_POSITION, END_WINDOW_Y_POSITION, END_WINDOW_WIDTH, END_WINDOW_HEIGHT);
        String endGame = moveReport.isCheckMate() ? "checkmate" : "stalemate";
        this.controller = controller;
        texture = new Texture(Gdx.files.internal("end_window/" + endGame + ".png"));
        addListener(new EndWindowListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    private class EndWindowListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            controller.removeActorFromStage(EndWindow.this);
            super.clicked(event, x, y);
        }
    }
}
