package com.mygdx.chess.client;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class GuiParams {
    public static final int WINDOW_SIZE = getWindowSize() - 150;
    static final int CHESSBOARD_POSITION = WINDOW_SIZE / 10;
    static final int CHESSBOARD_SIZE = WINDOW_SIZE * 80 / 100;
    static final int CHESS_PIECE_SIZE = CHESSBOARD_SIZE / 8; //
    static final int PROMOTION_WINDOW_X_POSITION = CHESSBOARD_POSITION + (CHESS_PIECE_SIZE * 2);
    static final int PROMOTION_WINDOW_Y_POSITION = CHESSBOARD_POSITION + (CHESS_PIECE_SIZE * 4);
    static final int PROMOTION_WINDOW_WIDTH = CHESS_PIECE_SIZE * 4;
    static final int PROMOTION_WINDOW_HEIGHT = CHESS_PIECE_SIZE;
    static final int END_WINDOW_X_POSITION = CHESSBOARD_POSITION + (CHESS_PIECE_SIZE * 3);
    static final int END_WINDOW_Y_POSITION = CHESSBOARD_POSITION + (CHESS_PIECE_SIZE * 4);
    static final int END_WINDOW_WIDTH = CHESS_PIECE_SIZE * 2;
    static final int END_WINDOW_HEIGHT = CHESS_PIECE_SIZE;

    private static int getWindowSize() {
        return Math.min(Lwjgl3ApplicationConfiguration.getDisplayMode().height,
            Lwjgl3ApplicationConfiguration.getDisplayMode().width);
    }
}
