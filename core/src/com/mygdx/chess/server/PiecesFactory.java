package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.*;

import java.util.ArrayList;
import java.util.List;

class PiecesFactory {
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

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceColor color) {// CHECK : 17.10.2023 poprawiona metoda
        List<ChessPiece> chessPieces = new ArrayList<>();
        ChessPieceType[] types = {ChessPieceType.ROOK, ChessPieceType.KNIGHT, ChessPieceType.RUNNER,
                ChessPieceType.QUEEN, ChessPieceType.KING, ChessPieceType.RUNNER, ChessPieceType.KNIGHT, ChessPieceType.ROOK};
        for (int i = 0; i < types.length; i++) {
            switch (types[i]) {
                case ROOK:
                    chessPieces.add(new Rook(color, i, color.getYFiguresPosition()));
                    break;
                case KNIGHT:
                    chessPieces.add(new Knight(color, i, color.getYFiguresPosition()));
                    break;
                case RUNNER:
                    chessPieces.add(new Runner(color, i, color.getYFiguresPosition()));
                    break;
                case QUEEN:
                    chessPieces.add(new Queen(color, i, color.getYFiguresPosition()));
                    break;
                case KING:
                    chessPieces.add(new King(color, i, color.getYFiguresPosition()));
                    break;
            }
        }
        return chessPieces;
    }
}
