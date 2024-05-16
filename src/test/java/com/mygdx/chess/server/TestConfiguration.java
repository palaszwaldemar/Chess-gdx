package com.mygdx.chess.server;

import java.util.Optional;

public class TestConfiguration {
    private final ChessPieceRepository repository;
    private final ChessPieceFactory chessPieceFactory;
    private final MoveValidator validator;
    private final TestBoardProvider boardProvider;

    TestConfiguration(ChessPieceRepository repository, ChessPieceFactory chessPieceFactory, MoveValidator validator,
                      TestBoardProvider boardProvider) {
        this.repository = repository;
        this.chessPieceFactory = chessPieceFactory;
        this.validator = validator;
        this.boardProvider = boardProvider;
        loadBoard();
    }

    private void loadBoard() {
        String[] rows = boardProvider.getBoard().split("\n");
        for (int y = 0; y < 8; y++) {
            loadRow(rows[y], rows.length - 1 - y);
        }
    }

    private void loadRow(String row, int startY) {
        String[] figures = row.split(" ");
        for (int x = 0; x < 8; x++) {
            loadFigures(figures[x], x, startY);
        }
    }

    private void loadFigures(String unicodeSymbol, int startX, int startY) {
        Optional<ChessPieceType> type = ChessPieceType.getTypeByUnicodeSymbol(unicodeSymbol);
        if (type.isPresent()) {
            ChessPieceDto dto =
                new ChessPieceDto(type.get(), type.get().getColorByUnicodeSymbol(unicodeSymbol), startX, startY);
            repository.add(chessPieceFactory.createChessPiece(dto, wasMoved(startX, startY)));
        }
    }

    private boolean wasMoved(int startX, int startY) {
        String[] rows = boardProvider.getInfoAboutMoves().split("\n");
        String[] row = rows[rows.length - 1 - startY].trim().split(" ");
        String value = row[startX];
        return value.equals("1");
    }

    boolean chessPieceMoveTest(int startX, int startY, int[][] allCorrectMoves) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((allCorrectMoves.length == 0 && validator.canMove(moveDto(startX, startY, i, j))) ||
                    isMoveMismatch(i, j, startX, startY, allCorrectMoves)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isMoveMismatch(int i, int j, int startX, int startY, int[][] allCorrectMoves) {
        boolean isCorrectMove = isCorrectMove(i, j, allCorrectMoves);
        boolean canMove = validator.canMove(moveDto(startX, startY, i, j));
        return isCorrectMove != canMove;
    }

    private boolean isCorrectMove(int x, int y, int[][] correctMove) {
        for (int[] move : correctMove) {
            if (move[0] == x && move[1] == y) {
                return true;
            }
        }
        return false;
    }

    private MoveDto moveDto(int startX, int startY, int endX, int endY) {
        ChessPiece chessPieceToDto = repository.getChessPiece(startX, startY).orElseThrow();
        return MoveDto.create(chessPieceToDto, endX, endY);
    }
}
