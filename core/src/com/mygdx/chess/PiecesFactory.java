package com.mygdx.chess;

import java.util.ArrayList;
import java.util.List;

public class PiecesFactory {
    public List<ChessPieceActor> getChessPieces() {
        List<ChessPieceActor> chessPieces = new ArrayList<>();
        chessPieces.addAll(listOfPawns(ChessPieceColor.WHITE, Cords.yToCords(GuiParams.CHESSBOARD_POSITION) + 1));
        chessPieces.addAll(listOfPawns(ChessPieceColor.BLACK, Cords.yToCords(GuiParams.CHESSBOARD_POSITION) + 6));
        return chessPieces;
    }

    private List<ChessPieceActor> listOfPawns(ChessPieceColor color, int y) {
        List<ChessPieceActor> chessPawns = new ArrayList<>();
        ChessPieceType type = ChessPieceType.PAWN;
        int x = Cords.xToCords(GuiParams.CHESSBOARD_POSITION);
        for (int i = 0; i < 8; i++) {
            ChessPieceActor chessPieceActor = new ChessPieceActor(new ChessPiece(), type, color, x + i, y);
            chessPawns.add(chessPieceActor);
        }
        return chessPawns;
    }
}
