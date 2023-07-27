package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {

    ChessPieceType[] chessPieceTypes = {ChessPieceType.ROOK, ChessPieceType.KNIGHT, ChessPieceType.RUNNER,
            ChessPieceType.QUEEN, ChessPieceType.KING, ChessPieceType.RUNNER,
            ChessPieceType.KNIGHT, ChessPieceType.ROOK};

    List<ChessPiece> getChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(listOfPawns(color));
        chessPieces.addAll(listOfRemainingChessPieces(color));
        return chessPieces;
    }

    private List<ChessPiece> listOfPawns(ChessPieceColor color) {
        List<ChessPiece> chessPawns = new ArrayList<>();
        ChessPieceType type = ChessPieceType.PAWN;
        for (int i = 0; i < 8; i++) {
            ChessPiece chessPiece = new ChessPiece(type, color, i, color.getYPawnsPosition());
            chessPawns.add(chessPiece);
        }
        return chessPawns;
    }

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            chessPieces.add(new ChessPiece(chessPieceTypes[i], color, i, color.getYFiguresPosition()));
        }
        return chessPieces;
    }
}
