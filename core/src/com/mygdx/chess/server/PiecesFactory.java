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
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(color, i, color.getYPawnsPosition());
            chessPawns.add(pawn);
        }
        return chessPawns;
    }

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
//            chessPieces.add(new ChessPiece(chessPieceTypes[i], color, i, color.getYFiguresPosition()));
        }
        return chessPieces;
    }
}
