package com.mygdx.chess.server;

import com.mygdx.chess.server.ChessPiece;
import com.mygdx.chess.server.ChessPieceColor;
import com.mygdx.chess.server.ChessPieceType;

import java.util.ArrayList;
import java.util.List;

public class PiecesFactory {
    public List<ChessPiece> getChessPieces() {
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
