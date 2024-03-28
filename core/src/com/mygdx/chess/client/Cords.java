package com.mygdx.chess.client;

class Cords {
    static int xToPixels(int x) {
        return x * 100;
    }

    static int yToPixels(int y) {
        return y * 100;
    }

    static int xToCords(int x) {
        return x / 100;
    }

    static int yToCords(int y) {
        return y / 100;
    }


}
