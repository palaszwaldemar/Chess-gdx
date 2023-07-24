package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {
    List<ChessPiece> getChessPieces() {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(listOfPawns(ChessPieceColor.WHITE,  1));
        chessPieces.addAll(listOfPawns(ChessPieceColor.BLACK,  6));
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
}
