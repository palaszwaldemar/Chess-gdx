package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {
    List<ChessPiece> getChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        int pawnsYPosition;
        int positionShift;
        int[] xValuesRooks = {0, 7};
        int[] xValuesKnights = {1, 6};
        int[] xValuesRunners = {2, 5};

        if (color == ChessPieceColor.WHITE) {
            pawnsYPosition = 1;
            positionShift = -1;
        } else {
            pawnsYPosition = 6;
            positionShift = 1;
        }

        int chessPiecesYPosition = pawnsYPosition + positionShift;

        chessPieces.addAll(listOfPawns(color, pawnsYPosition));
        chessPieces.addAll(listOfRemainingChessPieces(ChessPieceType.ROOK, color, xValuesRooks, chessPiecesYPosition));
        chessPieces.addAll(listOfRemainingChessPieces(ChessPieceType.KNIGHT, color, xValuesKnights, chessPiecesYPosition));
        chessPieces.addAll(listOfRemainingChessPieces(ChessPieceType.RUNNER, color, xValuesRunners, chessPiecesYPosition));
        chessPieces.add(new ChessPiece(ChessPieceType.QUEEN, color, 3, chessPiecesYPosition));
        chessPieces.add(new ChessPiece(ChessPieceType.KING, color, 4, chessPiecesYPosition));
        return chessPieces;
    }

    private List<ChessPiece> listOfPawns(ChessPieceColor color, int y) {
        List<ChessPiece> chessPawns = new ArrayList<>();
        ChessPieceType type = ChessPieceType.PAWN;
        for (int x = 0; x < 8; x++) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessPawns.add(chessPiece);
        }
        return chessPawns;
    }

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceType type, ChessPieceColor color, int[] xValues, int y) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        for (int x : xValues) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessPieces.add(chessPiece);
        }
        return chessPieces;
    }
}
