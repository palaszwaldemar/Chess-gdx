package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.*;

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
        chessPieces.add(new Knight(color, 1, color.getYFiguresPosition()));
        chessPieces.add(new Knight(color, 6, color.getYFiguresPosition()));
        chessPieces.add(new Runner(color, 2, color.getYFiguresPosition()));
        chessPieces.add(new Runner(color, 5, color.getYFiguresPosition()));
        chessPieces.add(new Queen(color, 3, color.getYFiguresPosition()));
//        for (int i = 0; i < 8; i++) {
//            chessPieces.add(new ChessPiece(chessPieceTypes[i], color, i, color.getYFiguresPosition()));
//        }
        return chessPieces;
    }
}
