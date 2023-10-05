package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.*;

import java.lang.reflect.InvocationTargetException;
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

    private List<ChessPiece> listOfRemainingChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();

        Class<? extends ChessPiece>[] pieceClasses = new Class[]{Rook.class, Knight.class, Runner.class,
                Queen.class, King.class, Runner.class, Knight.class, Rook.class};
        for (int i = 0; i < 8; i++) {
            try {
                ChessPiece piece = pieceClasses[i].getDeclaredConstructor(ChessPieceColor.class, int.class, int.class).
                        newInstance(color, i, color.getYFiguresPosition());
                chessPieces.add(piece);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return chessPieces;
    }
}
