package com.mygdx.chess.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.mygdx.chess.server.ChessPieceColor.BLACK;
import static com.mygdx.chess.server.ChessPieceColor.WHITE;
import static com.mygdx.chess.server.ChessPieceType.*;
import static org.junit.jupiter.api.Assertions.*;

class MoveValidatorStartingBoardTest {
    private ChessPieceRepository repository;
    private MoveValidator validator;

    @BeforeEach
    void setUp() {
        repository = new ChessPieceRepository();
        validator = new MoveValidator(repository);
        ChessPieceFactory factory = new ChessPieceFactory();
        repository.addAll(factory.createStartingPieces(WHITE));
        repository.addAll(factory.createStartingPieces(BLACK));
    }

    private MoveDto move(ChessPieceType type, ChessPieceColor color, int startX, int startY, int endX, int endY) {
        ChessPiece chessPieceToDto = null;
        for (ChessPiece chessPiece : repository.getChessPieces()) {
            if (chessPiece.getType() == type && chessPiece.getColor() == color && chessPiece.getX() == startX &&
                chessPiece.getY() == startY) {
                chessPieceToDto = chessPiece;
            }
        }
        return MoveDto.create(chessPieceToDto, endX, endY);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 1, 0, 2, true",
        "0, 1, 0, 3, true",
        "0, 1, 0, 4, false",
        "0, 1, -1, 0, false",
        "0, 1, 1, 1, false",
        "4, 1, 4, 2, true",
        "4, 1, 4, 3, true",
        "4, 1, 4, 4, false",
        "4, 1, -1, 0, false",
        "4, 1, 5, 1, false",
        "7, 1, 7, 2, true",
        "7, 1, 7, 3, true",
        "7, 1, 7, 4, false",
        "7, 1, -1, 0, false",
        "7, 1, 6, 2, false",
        "7, 1, 8, 2, false"
    })
    void correctMoveByWhitePawn(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(PAWN, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 0, 0, 1",
        "10, 10, 3, 3"
    })
    void moveByWhitePawnShouldThrowNullPointerException(int startX, int startY, int endX, int endY) {
        assertThrows(NullPointerException.class, () -> validator.canMove(move(PAWN, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 6, 0, 5, true",
        "0, 6, 0, 4, true",
        "0, 6, 0, 3, false",
        "0, 6, -1, 6, false",
        "0, 6, 1, 5, false",
        "4, 6, 4, 5, true",
        "4, 6, 4, 4, true",
        "4, 6, 4, 3, false",
        "4, 6, -1, 0, false",
        "4, 6, 4, 7, false",
        "7, 6, 7, 5, true",
        "7, 6, 7, 4, true",
        "7, 6, 7, 3, false",
        "7, 6, 6, 5, false",
        "7, 6, 6, 6, false",
        "7, 6, 7, 7, false"
    })
    void correctMoveByBlackPawn(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(PAWN, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 3, 0, 1",
        "10, 10, 3, 3"
    })
    void moveByBlackPawnShouldThrowNullPointerException(int startX, int startY, int endX, int endY) {
        assertThrows(NullPointerException.class, () -> validator.canMove(move(PAWN, BLACK, startX, startY, endX,
            endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 0, 1, false",
        "0, 0, 1, 0, false",
        "0, 0, 1, 1, false",
        "0, 0, -1, 1, false",
        "0, 0, -1, -1, false",
        "0, 0, -1, -1, false",
        "0, 0, 0, 5, false",
        "0, 0, 3, 6, false",
    })
    void correctMoveByWhiteLeftRook(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(ROOK, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "7, 0, 7, 1, false",
        "7, 0, 8, 0, false",
        "7, 0, 8, 1, false",
        "7, 0, 6, 1, false",
        "7, 0, 6, 6, false",
        "7, 0, -1, -1, false",
        "7, 0, 0, 5, false",
        "7, 0, 3, 6, false",
    })
    void correctMoveByWhiteRightRook(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(ROOK, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "4, 0, 4, 1, false",
        "4, 0, 5, 0, false",
        "4, 0, 5, 1, false",
        "4, 0, 3, 1, false",
        "4, 0, 3, 0, false",
        "4, 0, 5, 2, false",
        "4, 0, 3, 2, false",
        "4, 0, 2, 0, false",
        "4, 0, 6, 0, false"
    })
    void correctMoveByWhiteKing(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(KING, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "4, 7, 4, 6, false",
        "4, 7, 5, 7, false",
        "4, 7, 5, 6, false",
        "4, 7, 3, 6, false",
        "4, 7, 3, 7, false",
        "4, 7, 5, 5, false",
        "4, 7, 3, 5, false",
        "4, 7, 2, 7, false",
        "4, 7, 6, 7, false"
    })
    void correctMoveByBlackKing(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(KING, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 0, 0, 2, true",
        "1, 0, 2, 2, true",
        "1, 0, 3, 1, false",
        "1, 0, 0, 1, false",
        "1, 0, 2, 1, false",
        "1, 0, 3, 0, false",
        "1, 0, 3, 2, false",
        "1, 0, 0, 3, false",
        "1, 0, 2, 3, false"
    })
    void correctMoveByWhiteLeftKnight(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(KNIGHT, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "6, 0, 5, 2, true",
        "6, 0, 7, 2, true",
        "6, 0, 8, 1, false",
        "6, 0, 4, 1, false",
        "6, 0, 7, 1, false",
        "6, 0, 8, 0, false",
        "6, 0, 8, 2, false",
        "6, 0, 5, 3, false",
        "6, 0, 7, 3, false"
    })
    void correctMoveByWhiteRightKnight(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(KNIGHT, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 7, 0, 5, true",
        "1, 7, 2, 5, true",
        "1, 7, 3, 6, false",
        "1, 7, 0, 6, false",
        "1, 7, 2, 6, false",
        "1, 7, 3, 7, false",
        "1, 7, 3, 5, false",
        "1, 7, 0, 4, false",
        "1, 7, 2, 4, false"
    })
    void correctMoveByBlackLeftKnight(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(KNIGHT, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "6, 7, 5, 5, true",
        "6, 7, 7, 5, true",
        "6, 7, 8, 6, false",
        "6, 7, 5, 6, false",
        "6, 7, 7, 6, false",
        "6, 7, 8, 7, false",
        "6, 7, 8, 5, false",
        "6, 7, 5, 4, false",
        "6, 7, 7, 4, false"
    })
    void correctMoveByBlackRightKnight(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(KNIGHT, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "2, 0, 5, 5, false",
        "2, 0, 7, 5, false",
        "2, 0, 8, 6, false",
        "2, 0, 5, 6, false",
        "2, 0, 7, 6, false",
        "2, 0, 8, 7, false",
        "2, 0, 8, 5, false",
        "2, 0, 5, 4, false",
        "2, 0, 7, 4, false"
    })
    void correctMoveByWhiteLeftRunner(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(RUNNER, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "5, 0, 4, 0, false",
        "5, 0, 4, 1, false",
        "5, 0, 4, 2, false",
        "5, 0, 4, 3, false",
        "5, 0, 6, 0, false",
        "5, 0, 8, 7, false",
        "5, 0, 8, 5, false",
        "5, 0, 5, 4, false",
        "5, 0, 7, 4, false"
    })
    void correctMoveByWhiteRightRunner(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(RUNNER, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "2, 7, 1, 7, false",
        "2, 7, 1, 6, false",
        "2, 7, 2, 6, false",
        "2, 7, 3, 6, false",
        "2, 7, 4, 7, false",
        "2, 7, 8, 7, false",
        "2, 7, 8, 5, false",
        "2, 7, -1, 4, false",
        "2, 7, 7, 10, false"
    })
    void correctMoveByBlackLeftRunner(int startX, int startY, int endX, int endY, boolean expectedOutcome) {
        assertEquals(expectedOutcome, validator.canMove(move(RUNNER, BLACK, startX, startY, endX, endY)));
    }
}
