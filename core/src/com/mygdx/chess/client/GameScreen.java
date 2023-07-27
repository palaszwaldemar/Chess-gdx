package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final Chess game;
    private final OrthographicCamera camera;
    private final Stage stage;

    GameScreen(Chess chess) {
        this.game = chess;

        //przygotowanie elementów graficznych
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WINDOW_SIZE_WIDTH, GuiParams.WINDOW_SIZE_HEIGHT);
        stage = new Stage(new ScreenViewport(), game.getBatch());
        Gdx.input.setInputProcessor(stage);

        ChessboardGroup chessboardGroup = new ChessboardGroup();
        chessboardGroup.setPosition(GuiParams.CHESSBOARD_X_POSITION, GuiParams.CHESSBOARD_Y_POSITION);// TODO: 27.07.2023 przenieść ustawianie do ChessboardGroup (setBounds())
        stage.addActor(chessboardGroup);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
