package com.mygdx.chess.server;

import org.junit.jupiter.api.Test;

import static com.mygdx.chess.server.TestBoardProvider.*;
import static org.junit.jupiter.api.Assertions.*;

public class StalemateAndCheckmateTest {
    @Test
    void testStalemate() {
        TestConfiguration tc;
        tc = new TestConfiguration(STALEMATE_TEST_BOARD);
        assertTrue(tc.isStaleMate(0, 2, 1, 2));
        tc = new TestConfiguration(STALEMATE_TEST_BOARD);
        assertFalse(tc.isStaleMate(0, 2, 0, 1));
        tc = new TestConfiguration(STALEMATE_TEST_BOARD);
        assertFalse(tc.isStaleMate(7, 6, 0, 6));
        tc = new TestConfiguration(STALEMATE_TEST_BOARD);
        assertFalse(tc.isStaleMate(4, 0, 3, 0));
    }

    @Test
    void testCheckmate() {
        TestConfiguration tc;
        tc = new TestConfiguration(CHECKMATE_TEST_BOARD);
        assertTrue(tc.isCheckMate(7, 6, 1, 6));
        tc = new TestConfiguration(CHECKMATE_TEST_BOARD);
        assertFalse(tc.isCheckMate(7, 6, 0, 6));
        tc = new TestConfiguration(CHECKMATE_TEST_BOARD);
        assertFalse(tc.isCheckMate(1, 2, 1, 6));
        tc = new TestConfiguration(CHECKMATE_TEST_BOARD);
        assertFalse(tc.isCheckMate(1, 2, 1, 7));
    }
}
