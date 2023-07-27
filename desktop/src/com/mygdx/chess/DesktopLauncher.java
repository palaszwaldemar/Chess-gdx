package com.mygdx.chess;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.chess.client.Chess;
import com.mygdx.chess.client.GuiParams;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Chess");
        config.setWindowedMode(GuiParams.WINDOW_SIZE_WIDTH, GuiParams.WINDOW_SIZE_HEIGHT);
        config.setResizable(false);
        new Lwjgl3Application(new Chess(), config);
    }
}
