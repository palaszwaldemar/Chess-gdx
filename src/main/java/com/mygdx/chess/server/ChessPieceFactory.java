package com.mygdx.chess.server;

import java.util.ArrayList;
import java.util.List;

class ChessPieceFactory {
    List<ChessPiece> createStartingPieces(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        chessPieces.addAll(listOfPawns(color));
        chessPieces.addAll(listOfFigures(color));
        return chessPieces;
    }

    ChessPiece createPromotionFigure(MoveDto moveDto) {
        ChessPieceDto chessPieceDto =
            new ChessPieceDto(moveDto.promotionTypeSelected(), moveDto.inUse().getColor(), moveDto.x(), moveDto.y());
        return createChessPiece(chessPieceDto, false);
    }

    private List<ChessPiece> listOfPawns(ChessPieceColor color) {
        List<ChessPiece> chessPawns = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            chessPawns.add(
                createChessPiece(new ChessPieceDto(ChessPieceType.PAWN, color, i, color.getYPawnsPosition()), false));
        }
        return chessPawns;
    }

    private List<ChessPiece> listOfFigures(ChessPieceColor color) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        ChessPieceType[] types =
            {ChessPieceType.ROOK, ChessPieceType.KNIGHT, ChessPieceType.RUNNER, ChessPieceType.QUEEN,
                ChessPieceType.KING, ChessPieceType.RUNNER, ChessPieceType.KNIGHT, ChessPieceType.ROOK};
        for (int i = 0; i < types.length; i++) {
            chessPieces.add(createChessPiece(new ChessPieceDto(types[i], color, i, color.getYFiguresPosition()),
                false));
        }
        return chessPieces;
    }

    ChessPiece createChessPiece(ChessPieceDto chessPieceDto, boolean wasMoved) {
        ChessPiece piece = switch (chessPieceDto.type()) {
            case QUEEN -> new Queen(chessPieceDto);
            case ROOK -> new Rook(chessPieceDto);
            case RUNNER -> new Runner(chessPieceDto);
            case KNIGHT -> new Knight(chessPieceDto);
            case KING -> new King(chessPieceDto);
            case PAWN -> new Pawn(chessPieceDto);
        };
        piece.setMoved(wasMoved);
        return piece;
    }
}
