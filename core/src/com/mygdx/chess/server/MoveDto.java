package com.mygdx.chess.server;

public record MoveDto(ChessPiece inUse, int x, int y, ChessPieceType promotionTypeSelected) {
    public MoveDto withType(ChessPieceType typeSelected) {
        return new MoveDto(inUse, x, y, typeSelected);
    }

    public static MoveDto create(ChessPiece inUse, int x, int y, ChessPieceType promotionTypeSelected) {
        return new MoveDto(inUse, x, y, promotionTypeSelected);
    }

    public static MoveDto create(ChessPiece inUse, int x, int y) {
        return create(inUse, x, y, null);
    }
}
//todo czy dałoby radę zrobić ChessPieceDto in use?

