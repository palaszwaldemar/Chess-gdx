package com.mygdx.chess.server;

import java.util.Optional;

import static com.mygdx.chess.server.TestBoardProvider.gameBoardForPawnsTest;

public class TestConfiguration {
    private final ChessPieceRepository repository;
    private final ChessPieceFactory chessPieceFactory;
    private final MoveValidator validator;

    TestConfiguration(ChessPieceRepository repository, ChessPieceFactory chessPieceFactory, MoveValidator validator) {
        this.repository = repository;
        this.chessPieceFactory = chessPieceFactory;
        this.validator = validator;
    }

    void loadBoard(String board) {
        String[] rows = board.split("\n");
        for (int i = 0; i < 8; i++) {
            loadRow(rows[i], i);
        }
    }

    private void loadRow(String row, int startY) {
        String[] figures = row.split(" ");
        for (int x = 0; x < 8; x++) {
            loadFigures(figures[x], x, startY);
        }
    }

    private void loadFigures(String unicodeSymbol, int startX, int startY) {
        boolean wasMoved = setMoved(startX, startY);
        Optional<ChessPieceType> type = ChessPieceType.getTypeByUnicodeSymbol(unicodeSymbol);
        if (type.isPresent()) {
            ChessPieceDto dto =
                new ChessPieceDto(type.get(), type.get().getColorByUnicodeSymbol(unicodeSymbol), startX, 7 - startY);
            repository.add(chessPieceFactory.createChessPiece(dto, wasMoved));
        }
    }

    private boolean setMoved(int startX, int startY) {
        boolean moved = false;
        String infoAboutMoves = gameBoardForPawnsTest(true);
        String[] rows = infoAboutMoves.split("\n");
        for (int x = 0; x < rows.length; x++) {
            String[] move = rows[x].split(" ");
            for (int y = 0; y < move.length; y++) {
                if (startX == x && startY == y) {
                    if (move[y].equals("0")) {
                        moved = false;
                        break;
                    }
                    if (move[y].equals("1")) {
                        moved = true;
                        break;
                    }
                }
            }
        }
        return moved;
    }

    boolean chessPieceMoveTest(int startX, int startY, int[][] allCorrectMoves) {
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
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
