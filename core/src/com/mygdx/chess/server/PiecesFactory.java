package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {
    List<ChessPiece> getChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        int pawnsYPosition;
        int positionShift;

        if (color == ChessPieceColor.WHITE) {
            pawnsYPosition = 1;
            positionShift = -1;
        } else {
            pawnsYPosition = 6;
            positionShift = 1;
        }

        int chessPiecesYPosition = pawnsYPosition + positionShift;

        chessPieces.addAll(listOfPawns(color, pawnsYPosition));
        chessPieces.addAll(listOfRooks(color, chessPiecesYPosition));
        chessPieces.addAll(listOfKnight(color, chessPiecesYPosition));
        chessPieces.addAll(listOfRunners(color, chessPiecesYPosition));
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

    private List<ChessPiece> listOfRooks(ChessPieceColor color, int y) {
        List<ChessPiece> chessRooks = new ArrayList<>();
        ChessPieceType type = ChessPieceType.ROOK;
        int[] xValues = {0, 7};
        for (int x : xValues) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessRooks.add(chessPiece);
        }
        return chessRooks;
    }

    private List<ChessPiece> listOfKnight(ChessPieceColor color, int y) {
        List<ChessPiece> chessKnights = new ArrayList<>();
        ChessPieceType type = ChessPieceType.KNIGHT;
        int[] xValues = {1, 6};
        for (int x : xValues) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessKnights.add(chessPiece);
        }
        return chessKnights;
    }

    private List<ChessPiece> listOfRunners(ChessPieceColor color, int y) {
        List<ChessPiece> chessRunners = new ArrayList<>();
        ChessPieceType type = ChessPieceType.RUNNER;
        int[] xValues = {2, 5};
        for (int x : xValues) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessRunners.add(chessPiece);
        }
        return chessRunners;
    }

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceColor color, int[] xValues, int y) {// CHECK: 25.07.2023 chciałem zrobić wspólną metodę do generowania figur szachowych (oprócz pionków)
        List<ChessPiece> chessPieces = new ArrayList<>();
        ChessPieceType type = ChessPieceType.RUNNER;
        for (int x : xValues) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessPieces.add(chessPiece);
        }
        return chessPieces;
    }
}
