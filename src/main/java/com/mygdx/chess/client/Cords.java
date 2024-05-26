package com.mygdx.chess.client;

import static com.mygdx.chess.client.GuiParams.CHESS_PIECE_SIZE;

class Cords {
    static int xToPixels(int x) {
        return x * CHESS_PIECE_SIZE;
    }

    static int yToPixels(int y) {
        return y * CHESS_PIECE_SIZE;
    }

    static int xToCords(int x) {
        return x / CHESS_PIECE_SIZE;
    }

    static int yToCords(int y) {
        return y / CHESS_PIECE_SIZE;
    }
}
