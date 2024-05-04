package com.mygdx.chess.server;

public class TestBoardProvider {
    static String gameBoardForPawnsTest(boolean infoAboutMoves) {
        if (infoAboutMoves) {
            return """
                0 0 0 - - 0 0 0
                1 0 - 1 0 0 - 0
                1 1 1 1 - - - -
                - - - 1 - 1 - -
                1 - - - 1 - 1 -
                - 1 - - - - - -
                0 - 0 - - - 0 0
                0 0 0 0 0 - - 0
                """;
        }
        return """
            ♜ ♞ ♝ ⏥ ⏥ ♝ ♞ ♜
            ♙ ♟ ⏥ ♚ ♟ ♟ ⏥ ♟
            ♟ ♙ ♟ ♟ ⏥ ⏥ ⏥ ⏥
            ⏥ ⏥ ⏥ ♛ ⏥ ♘ ⏥ ⏥
            ♗ ⏥ ⏥ ⏥ ♙ ⏥ ♟ ⏥
            ⏥ ♙ ⏥ ⏥ ⏥ ⏥ ⏥ ⏥
            ♙ ⏥ ♙ ⏥ ⏥ ⏥ ♙ ♙
            ♖ ♘ ♗ ♕ ♔ ⏥ ⏥ ♖
            """;
    }
}
