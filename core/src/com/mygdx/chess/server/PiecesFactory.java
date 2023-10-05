package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {

    /*ChessPieceType[] chessPieceTypes = {ChessPieceType.ROOK, ChessPieceType.KNIGHT, ChessPieceType.RUNNER,
            ChessPieceType.QUEEN, ChessPieceType.KING, ChessPieceType.RUNNER,
            ChessPieceType.KNIGHT, ChessPieceType.ROOK};*/

    List<ChessPiece> getChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(listOfPawns(color));
        chessPieces.addAll(listOfRemainingChessPieces(color));
        return chessPieces;
    }

    private List<ChessPiece> listOfPawns(ChessPieceColor color) {
        List<ChessPiece> chessPawns = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            chessPawns.add(new Pawn(color, i, color.getYPawnsPosition()));
        }
        return chessPawns;
    }

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.add(new Rook(color, 0, color.getYFiguresPosition()));
        chessPieces.add(new Rook(color, 7, color.getYFiguresPosition()));
//        for (int i = 0; i < 8; i++) {
//            chessPieces.add(new ChessPiece(chessPieceTypes[i], color, i, color.getYFiguresPosition()));
//        }
        return chessPieces;
    }
}
