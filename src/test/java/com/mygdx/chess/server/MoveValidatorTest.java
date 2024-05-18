package com.mygdx.chess.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.mygdx.chess.server.TestBoardProvider.*;
import static org.junit.jupiter.api.Assertions.*;

class MoveValidatorTest {
    private ChessPieceRepository repository;
    private MoveValidator validator;
    private ChessPieceFactory chessPieceFactory;

    @BeforeEach
    void setUp() {
        chessPieceFactory = new ChessPieceFactory();
        repository = new ChessPieceRepository();
        validator = new MoveValidator(repository);
    }

    private static Stream<Arguments> pawns() {
        return Stream.of(Arguments.of(2, 1, new int[][]{{2, 2}, {2, 3}}), Arguments.of(2, 5, new int[0][0]),
            Arguments.of(0, 6, new int[][]{{1, 7}}), Arguments.of(5, 6, new int[][]{{5, 5}}),
            Arguments.of(4, 3, new int[][]{{4, 4}, {3, 4}}), Arguments.of(1, 5, new int[0][0]));
    }

    @ParameterizedTest
    @MethodSource("pawns")
    void testPawnsMovesValidity(int startX, int startY, int[][] allCorrectMoves) {
        TestConfiguration tc = new TestConfiguration(repository, chessPieceFactory, validator, PAWNS_TEST_BOARD);
        assertTrue(tc.chessPieceValidityMoveTest(startX, startY, allCorrectMoves));
    }

    private static Stream<Arguments> knights() {
        return Stream.of(Arguments.of(3, 6, new int[0][0]),
            Arguments.of(2, 2, new int[][]{{0, 3}, {0, 1}, {1, 0}, {3, 0}, {4, 1}, {4, 3}, {3, 4}, {1, 4}}),
            Arguments.of(3, 3, new int[0][0]), Arguments.of(5, 2, new int[][]{{4, 0}, {7, 3}, {6, 4}}));
    }

    @ParameterizedTest
    @MethodSource("knights")
    void testKnightsMovesValidity(int startX, int startY, int[][] allCorrectsMoves) {
        TestConfiguration tc = new TestConfiguration(repository, chessPieceFactory, validator, KNIGHTS_TEST_BOARD);
        assertTrue(tc.chessPieceValidityMoveTest(startX, startY, allCorrectsMoves));
    }

    private static Stream<Arguments> runners() {
        return Stream.of(Arguments.of(2, 3,
                new int[][]{{0, 5}, {0, 1}, {1, 2}, {1, 4}, {3, 2}, {3, 4}, {4, 1}, {4, 5}, {5, 6}, {6, 7}}),
            Arguments.of(4, 1, new int[0][0]), Arguments.of(7, 3, new int[][]{{6, 4}, {5, 5}}),
            Arguments.of(7, 7, new int[0][0]));
    }

    @ParameterizedTest
    @MethodSource("runners")
    void testRunnersMovesValidity(int startX, int startY, int[][] allCorrectsMoves) {
        TestConfiguration tc = new TestConfiguration(repository, chessPieceFactory, validator, RUNNERS_TEST_BOARD);
        assertTrue(tc.chessPieceValidityMoveTest(startX, startY, allCorrectsMoves));
    }

    private static Stream<Arguments> rooks() {
        return Stream.of(Arguments.of(2, 3, new int[][]{{0, 3}, {1, 3}, {2, 2}, {2, 4}, {2, 5}, {2, 6}, {3, 3}}),
            Arguments.of(3, 4,
                new int[][]{{0, 4}, {1, 4}, {2, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}, {3, 0}, {3, 1}, {3, 2}, {3, 3},
                    {3, 5}, {3, 6}, {3, 7}}), Arguments.of(4, 1, new int[][]{{4, 2}}),
            Arguments.of(6, 6, new int[0][0]));
    }

    @ParameterizedTest
    @MethodSource("rooks")
    void testRooksMovesValidity(int startX, int startY, int[][] allCorrectsMoves) {
        TestConfiguration tc = new TestConfiguration(repository, chessPieceFactory, validator, ROOKS_TEST_BOARD);
        assertTrue(tc.chessPieceValidityMoveTest(startX, startY, allCorrectsMoves));
    }
}
