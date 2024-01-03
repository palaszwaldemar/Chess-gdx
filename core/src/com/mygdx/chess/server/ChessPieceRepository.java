package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class ChessPieceRepository {
    private final List<ChessPiece> chessPieces = new ArrayList<>();

    public ChessPieceRepository(PiecesFactory piecesFactory) {
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
    }

    public List<ChessPiece> getChessPiecesByColor(ChessPieceColor color) {
        List<ChessPiece> chessPiecesByColor = new ArrayList<>();
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.hasColor(color)) {
                chessPiecesByColor.add(chessPiece);
            }
        }
        return chessPiecesByColor;
    }

    public List<ChessPiece> getRooksByColor(ChessPieceColor color) {
        List<ChessPiece> rooks = new ArrayList<>();
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.hasColor(color) && chessPiece.hasType(ChessPieceType.ROOK)) {
                rooks.add(chessPiece);
            }
        }
        return rooks;
    }

    List<ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
