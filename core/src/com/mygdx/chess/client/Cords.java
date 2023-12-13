package com.mygdx.chess.client;

public class Cords {
    public static int xToPixels(int x) {
        return x * 100;
    }

    public static int yToPixels(int y) {
        return y * 100;
    }

    public static int xToCords(int x) {
        return x / 100;
    }

    public static int yToCords(int y) {
        return y / 100;
    }
}
