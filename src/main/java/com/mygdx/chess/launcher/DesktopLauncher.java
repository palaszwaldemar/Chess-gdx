package com.mygdx.chess.launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.chess.client.Chess;

import static com.mygdx.chess.client.GuiParams.WINDOW_SIZE;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Chess");
        config.setWindowedMode(WINDOW_SIZE, WINDOW_SIZE);
        config.setResizable(false);
        new Lwjgl3Application(new Chess(), config);
    }
}
