package com.mygdx.chess.server;

import java.util.List;

public record ChessGameDto(List<ChessPiece> whiteChessPieces, List<ChessPiece> blackChessPieces,
                           ChessPieceColor activeColor) {
}
