package com.mygdx.chess.server;

import java.util.Optional;

public enum ChessPieceType {
    PAWN("♙", "♟"),
    ROOK("♖", "♜"),
    KNIGHT("♘", "♞"),
    RUNNER("♗", "♝"),
    QUEEN("♕", "♛"),
    KING("♔", "♚");

    private final String unicodeWhite;
    private final String unicodeBlack;

    ChessPieceType(String unicodeWhite, String unicodeBlack) {
        this.unicodeWhite = unicodeWhite;
        this.unicodeBlack = unicodeBlack;
    }

    static Optional<ChessPieceType> getTypeByUnicodeSymbol(String unicodeSymbol) {
      for (ChessPieceType value : values()) {
          if (unicodeSymbol.equals(value.unicodeWhite) || unicodeSymbol.equals(value.unicodeBlack)) {
              return Optional.of(value);
          }
      }
        return Optional.empty();
    }

    ChessPieceColor getColorByUnicodeSymbol(String unicodeSymbol){
        return unicodeSymbol.equals(unicodeWhite) ? ChessPieceColor.WHITE : ChessPieceColor.BLACK;
    }
}
