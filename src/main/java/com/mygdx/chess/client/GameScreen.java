package com.mygdx.chess.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

import static com.mygdx.chess.client.GuiParams.*;
import static com.mygdx.chess.server.ChessPieceColor.BLACK;
import static com.mygdx.chess.server.ChessPieceColor.WHITE;

class GameScreen implements Screen {
    private final Chess game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Controller controller;

    GameScreen(Chess chess) {
        this.game = chess;
        Image background = new Image(new Texture(Gdx.files.internal("chessboard/chessboard.png")));
        background.setBounds(CHESSBOARD_POSITION, CHESSBOARD_POSITION, CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        stage = new Stage(new ScreenViewport(), game.getBatch());
        stage.addActor(background);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_SIZE, WINDOW_SIZE);
        Gdx.input.setInputProcessor(stage);
        PlayerGroup whitePlayer = new PlayerGroup(WHITE);
        PlayerGroup blackPlayer = new PlayerGroup(BLACK);
        controller = new Controller(stage, List.of(whitePlayer, blackPlayer));
        attach(whitePlayer);
        attach(blackPlayer);
        controller.startGame();
    }

    private void attach(PlayerGroup playerGroup) {
        stage.addActor(playerGroup);
        playerGroup.setController(controller);
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
