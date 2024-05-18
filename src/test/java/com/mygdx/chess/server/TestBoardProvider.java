package com.mygdx.chess.server;

public enum TestBoardProvider {
    PAWNS_TEST_BOARD("""
        ♜ ♞ ♝ ⏥ ⏥ ♝ ♞ ♜
        ♙ ♟ ⏥ ♚ ♟ ♟ ⏥ ♟
        ♟ ♙ ♟ ♟ ⏥ ⏥ ⏥ ⏥
        ⏥ ⏥ ⏥ ♛ ⏥ ♘ ⏥ ⏥
        ♗ ⏥ ⏥ ⏥ ♙ ⏥ ♟ ⏥
        ⏥ ♙ ⏥ ⏥ ⏥ ⏥ ⏥ ⏥
        ♙ ⏥ ♙ ⏥ ⏥ ⏥ ♙ ♙
        ♖ ♘ ♗ ♕ ♔ ⏥ ⏥ ♖
        """, """
         0 0 0 - - 0 0 0
         1 0 - 1 0 0 - 0
         1 1 1 1 - - - -
         - - - 1 - 1 - -
         1 - - - 1 - 1 -
         - 1 - - - - - -
         0 - 0 - - - 0 0
         0 0 0 0 0 - - 0
        """), KNIGHTS_TEST_BOARD("""
        ♜ ⏥ ♝ ♛ ♚ ♝ ⏥ ♜
        ♟ ♟ ♟ ♞ ♟ ♟ ♟ ♟
        ⏥ ⏥ ♗ ♟ ♕ ⏥ ⏥ ⏥
        ⏥ ♗ ⏥ ⏥ ♖ ♙ ⏥ ⏥
        ⏥ ⏥ ⏥ ♘ ⏥ ⏥ ⏥ ⏥
        ⏥ ♙ ♞ ⏥ ⏥ ♘ ⏥ ⏥
        ♙ ⏥ ♙ ♙ ♙ ♙ ♙ ♖
        ⏥ ⏥ ⏥ ⏥ ⏥ ⏥ ♔ ⏥
        """, """
        0 - 0 0 0 0 - 0
        0 0 0 1 0 0 0 0
        - - 1 1 1 - - -
        - 1 - - 1 1 - -
        - - - 1 - - - -
        - 1 1 - - 1 - -
        0 - 0 0 0 0 0 1
        - - - - - - 1 -
        """), RUNNERS_TEST_BOARD("""
        ♜ ♞ ⏥ ⏥ ♚ ⏥ ⏥ ♝
        ♟ ⏥ ♟ ⏥ ♟ ⏥ ♟ ♟
        ⏥ ♟ ♞ ♟ ⏥ ♟ ⏥ ⏥
        ♘ ⏥ ⏥ ⏥ ♙ ♛ ⏥ ⏥
        ⏥ ♙ ♝ ⏥ ♜ ♙ ⏥ ♗
        ♕ ⏥ ♙ ⏥ ⏥ ⏥ ♘ ⏥
        ♙ ⏥ ♖ ♙ ♗ ⏥ ♙ ♙
        ⏥ ⏥ ⏥ ⏥ ♔ ⏥ ⏥ ♖
        """, """
        0 0 - - 0 - - 1
        0 - 0 - 0 - 0 0
        - 1 1 1 - 1 - -
        1 - - - 1 1 - -
        - 1 1 - 1 1 - 1
        1 - 1 - - - 1 -
        0 - 1 0 1 - 0 0
        - - - - 0 - - 0
        """), ROOKS_TEST_BOARD("""
        ⏥ ♞ ♝ ⏥ ⏥ ⏥ ♚ ⏥
        ♟ ♟ ♟ ⏥ ♟ ♟ ♜ ♟
        ⏥ ♕ ⏥ ⏥ ⏥ ⏥ ♟ ⏥
        ⏥ ⏥ ⏥ ♜ ⏥ ⏥ ⏥ ⏥
        ⏥ ⏥ ♖ ⏥ ♙ ⏥ ⏥ ⏥
        ⏥ ⏥ ⏥ ⏥ ♛ ⏥ ⏥ ♙
        ♙ ♙ ♗ ⏥ ♖ ⏥ ♙ ⏥
        ⏥ ⏥ ⏥ ⏥ ♔ ⏥ ♘ ⏥
        """, """
        - 0 0 - - - 1 -
        0 0 0 - 0 0 1 0
        - 1 - - - - 0 -
        - - - 1 - - - -
        - - 1 - 1 - - -
        - - - - 1 - - 1
        0 0 1 - 1 - 0 -
        - - - - 0 - 0 -
        """), KINGS_TEST_BOARD("""
        ♜ ⏥ ⏥ ⏥ ⏥ ♝ ⏥ ♜
        ♟ ♟ ⏥ ⏥ ⏥ ♟ ⏥ ♞
        ♙ ⏥ ⏥ ⏥ ♙ ⏥ ⏥ ⏥
        ⏥ ⏥ ♚ ⏥ ⏥ ⏥ ⏥ ⏥
        ⏥ ⏥ ⏥ ⏥ ⏥ ⏥ ♔ ♖
        ⏥ ⏥ ⏥ ♟ ⏥ ⏥ ⏥ ⏥
        ⏥ ⏥ ⏥ ♙ ⏥ ⏥ ⏥ ⏥
        ♖ ♘ ⏥ ♕ ⏥ ♗ ⏥ ⏥
        """, """
        0 - - - - 0 - 0
        0 0 - - - 0 - 1
        1 - - - 1 - - -
        - - 1 - - - - -
        - - - - - - 1 1
        - - - 1 - - - -
        - - - 0 - - - -
        1 1 - 1 - 1 - -
        """);
    private final String board;
    private final String infoAboutMovesBoard;

    TestBoardProvider(String board, String infoAboutMovesBoard) {
        this.board = board;
        this.infoAboutMovesBoard = infoAboutMovesBoard;
    }

    String getBoard() {
        return board;
    }

    String getInfoAboutMoves() {
        return infoAboutMovesBoard;
    }
}
