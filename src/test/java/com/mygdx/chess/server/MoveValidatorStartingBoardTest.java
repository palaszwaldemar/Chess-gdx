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

class MoveValidatorInProgresGameTest {
    private ChessPieceRepository repository;
    private MoveValidator validator;

    @BeforeEach
    void setUp() {
        repository = new ChessPieceRepository();
        validator = new MoveValidator(repository);
        prepareChessBoard();
    }

    private void prepareChessBoard() {
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 0, 2)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 1, 1)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 2, 3)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 3, 2)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 4, 1)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 5, 1)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 6, 2)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, WHITE, 7, 1)));

        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 0, 3)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 1, 6)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 2, 6)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 3, 4)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 5, 6)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 6, 6)));
        repository.add(new Pawn(new ChessPieceDto(PAWN, BLACK, 7, 5)));

        repository.add(new Rook(new ChessPieceDto(ROOK, WHITE, 0, 0)));
        repository.add(new Rook(new ChessPieceDto(ROOK, WHITE, 6, 0)));
        repository.add(new Rook(new ChessPieceDto(ROOK, BLACK, 0, 6)));
        repository.add(new Rook(new ChessPieceDto(ROOK, BLACK, 7, 7)));

        repository.add(new Knight(new ChessPieceDto(KNIGHT, WHITE, 2, 2)));
        repository.add(new Knight(new ChessPieceDto(KNIGHT, WHITE, 5, 2)));
        repository.add(new Knight(new ChessPieceDto(KNIGHT, BLACK, 1, 7)));
        repository.add(new Knight(new ChessPieceDto(KNIGHT, BLACK, 5, 5)));

        repository.add(new Runner(new ChessPieceDto(RUNNER, WHITE, 4, 2)));
        repository.add(new Runner(new ChessPieceDto(RUNNER, WHITE, 7, 2)));
        repository.add(new Runner(new ChessPieceDto(RUNNER, BLACK, 3, 5)));
        repository.add(new Runner(new ChessPieceDto(RUNNER, BLACK, 3, 6)));

        repository.add(new Queen(new ChessPieceDto(QUEEN, WHITE, 5, 4)));
        repository.add(new Queen(new ChessPieceDto(QUEEN, BLACK, 1, 4)));

        repository.add(new King(new ChessPieceDto(KING, WHITE, 3, 0)));
        repository.add(new King(new ChessPieceDto(KING, BLACK, 3, 7)));
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
        "1, 4, 0, 4, true",
        "1, 4, 2, 3, true",
        "1, 4, 3, 2, false",
        "1, 4, 3, 4, false",
        "1, 4, 3, 5, false",
        "1, 4, 2, 5, true",
        "1, 4, -1, 4, false",
        "1, 4, 1, 1, true",
        "1, 4, 0, 2, false"
    })
    void correctMoveByBlackQueen(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(QUEEN, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "5, 4, 5, 5, true",
        "5, 4, 7, 6, true",
        "5, 4, 3, 6, true",
        "5, 4, 5, 2, false",
        "5, 4, 6, 2, false",
        "5, 4, 4, 2, false",
        "5, 4, 2, 7, false",
        "5, 4, 3, 4, true",
        "5, 4, 0, 2, false",
        "5, 4, 5, 3, true"
    })
    void correctMoveByWhiteQueen(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(QUEEN, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "3, 0, 1, 0, false",
        "3, 0, 2, 0, true",
        "3, 0, 2, 1, true",
        "3, 0, 4, 0, true",
        "3, 0, 4, 1, false",
        "3, 0, 3, 2, false",
        "3, 0, -1, 2, false",
        "3, 0, 6, 7, false"
    })
    void correctMoveByWhiteKing(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(KING, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "3, 7, 2, 7, true",
        "3, 7, 1, 7, false",
        "3, 7, 2, 6, false",
        "3, 7, 3, 6, false",
        "3, 7, 4, 6, true",
        "3, 7, 4, 7, true",
        "3, 7, 3, 8, false",
        "3, 7, 6, 3, false"
    })
    void correctMoveByBlackKing(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(KING, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "2, 2, 3, 0, false",
        "2, 2, 1, 4, true",
        "2, 2, 4, 3, true",
        "2, 2, 6, 4, false",
        "5, 2, 6, 0, false",
        "5, 2, 4, 0, true",
        "5, 2, 4, 4, true",
        "5, 2, 8, 3, false"
    })
    void correctMoveByWhiteKnights(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(KNIGHT, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 7, 0, 5, true",
        "1, 7, 2, 5, true",
        "1, 7, -1, 7, false",
        "1, 7, 2, 7, false",
        "5, 5, 6, 4, false",
        "5, 5, 4, 5, false",
        "5, 5, 5, 6, false",
        "5, 5, 4, 7, true"
    })
    void correctMoveByBlackKnights(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(KNIGHT, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "4, 2, 4, 4, false",
        "4, 2, 3, 2, false",
        "4, 2, 2, 1, false",
        "4, 2, 6, 4, true",
        "7, 2, 5, 0, true",
        "7, 2, 6, 1, true",
        "7, 2, 6, 3, true",
        "7, 2, 4, 5, false"
    })
    void correctMoveByWhiteRunners(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(RUNNER, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "3, 5, 6, 2, true",
        "3, 5, 7, 1, false",
        "3, 5, 1, 3, true",
        "3, 5, 5, 3, true",
        "3, 6, 0, 3, false",
        "3, 6, 1, 4, false",
        "3, 6, 5, 4, true",
        "3, 6, 0, 5, false"
    })
    void correctMoveByBlackRunners(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(RUNNER, BLACK, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 2, 0, 3, false",
        "0, 2, 0, 4, false",
        "0, 2, 1, 3, false",
        "0, 2, 0, 1, false",
        "1, 1, 1, 2, true",
        "1, 1, 1, 3, true",
        "4, 1, 4, 2, false",
        "4, 1, 5, 2, false",
        "4, 1, 4, 3, false",
        "2, 3, 2, 4, true",
        "2, 3, 3, 4, true"
    })
    void correctMoveByWhitePawns(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(PAWN, WHITE, startX, startY, endX, endY)));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 3, 0, 2, false",
        "1, 6, 1, 4, false",
        "2, 6, 2, 4, true",
        "3, 4, 2, 3, true",
        "5, 6, 4, 6, false",
        "6, 6, 6, 4, true",
        "7, 5, 7, 4, true",
        "3, 4, 3, 2, false",
        "2, 6, 5, 1, false",
        "1, 6, 10, 9, false",
        "6, 5, -1, 4, false"
    })
    void correctMoveByBlackPawns(int startX, int startY, int endX, int endY, boolean expectedOutCome) {
        assertEquals(expectedOutCome, validator.canMove(move(PAWN, BLACK, startX, startY, endX, endY)));
    }
}
