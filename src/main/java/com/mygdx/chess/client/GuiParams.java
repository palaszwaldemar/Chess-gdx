package com.mygdx.chess.client;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class GuiParams {
    public static final int WINDOW_SIZE = getWindowSize() - 100;
    static final int CHESSBOARD_X_POSITION = 100;
    static final int CHESSBOARD_Y_POSITION = 100;
    static final int CHESS_PIECE_SIZE = 100;
    static final int CHESSBOARD_WIDTH = 800;
    static final int CHESSBOARD_HEIGHT = 800;
    static final int PROMOTION_WINDOW_X_POSITION = 300;
    static final int PROMOTION_WINDOW_Y_POSITION = 500;
    static final int PROMOTION_WINDOW_WIDTH = 400;
    static final int PROMOTION_WINDOW_HEIGHT = 100;
    static final int END_WINDOW_X_POSITION = 400;
    static final int END_WINDOW_Y_POSITION = 500;
    static final int END_WINDOW_WIDTH = 200;
    static final int END_WINDOW_HEIGHT = 100;

    private static int getWindowSize() {
        return Math.min(Lwjgl3ApplicationConfiguration.getDisplayMode().height,
            Lwjgl3ApplicationConfiguration.getDisplayMode().width);
    }
}
