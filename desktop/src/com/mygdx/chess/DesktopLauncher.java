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
//        config.setWindowPosition(-1050, 200); // TODO: 28.07.2023 to remove
        config.setWindowPosition(900, 30); // TODO: 28.07.2023 to remove (na lekcję)
        new Lwjgl3Application(new Chess(), config);
    }
}
