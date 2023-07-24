package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {
    List<ChessPiece> getChessPieces() {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(listOfPawns(ChessPieceColor.WHITE,  1));
        chessPieces.addAll(listOfPawns(ChessPieceColor.BLACK,  6));
        chessPieces.addAll(listOfRooks(ChessPieceColor.WHITE, 0));
        chessPieces.addAll(listOfRooks(ChessPieceColor.BLACK, 7));
        chessPieces.addAll(listOfKnight(ChessPieceColor.WHITE, 0));
        chessPieces.addAll(listOfKnight(ChessPieceColor.BLACK, 7));
        chessPieces.addAll(listOfRunners(ChessPieceColor.WHITE, 0));
        chessPieces.addAll(listOfRunners(ChessPieceColor.BLACK, 7));
        chessPieces.add(new ChessPiece(ChessPieceType.QUEEN, ChessPieceColor.WHITE, 3 ,0));
        chessPieces.add(new ChessPiece(ChessPieceType.QUEEN, ChessPieceColor.BLACK, 3 ,7));
        chessPieces.add(new ChessPiece(ChessPieceType.KING, ChessPieceColor.WHITE, 4 ,0));
        chessPieces.add(new ChessPiece(ChessPieceType.KING, ChessPieceColor.BLACK, 4 ,7));
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
        for (int x = 0; x < 8; x = x + 7) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessRooks.add(chessPiece);
        }
        return chessRooks;
    }

    private List<ChessPiece> listOfKnight(ChessPieceColor color, int y) {
        List<ChessPiece> chessKnights = new ArrayList<>();
        ChessPieceType type = ChessPieceType.KNIGHT;
        for (int x = 1; x < 7; x = x + 5) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessKnights.add(chessPiece);
        }
        return chessKnights;
    }

    private List<ChessPiece> listOfRunners(ChessPieceColor color, int y) {
        List<ChessPiece> chessRunners = new ArrayList<>();
        ChessPieceType type = ChessPieceType.RUNNER;
        for (int x = 2; x < 6; x = x + 3) {
            ChessPiece chessPiece = new ChessPiece(type, color, x, y);
            chessRunners.add(chessPiece);
        }
        return chessRunners;
    }
}
