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
import com.mygdx.chess.server.ChessPieceColor;

import java.util.List;

class GameScreen implements Screen {
    private final Chess game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Controller controller;

    GameScreen(Chess chess) {
        this.game = chess;
        Image background = new Image(new Texture(Gdx.files.internal("chessboard/chessboard.png")));
        background.setBounds(GuiParams.CHESSBOARD_X_POSITION, GuiParams.CHESSBOARD_Y_POSITION,
            GuiParams.CHESSBOARD_WIDTH, GuiParams.CHESSBOARD_HEIGHT);
        stage = new Stage(new ScreenViewport(), game.getBatch());
        stage.addActor(background);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WINDOW_SIZE_WIDTH, GuiParams.WINDOW_SIZE_HEIGHT);
        Gdx.input.setInputProcessor(stage);
        PlayerGroup whitePlayer = new PlayerGroup(ChessPieceColor.WHITE);
        PlayerGroup blackPlayer = new PlayerGroup(ChessPieceColor.BLACK);
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
