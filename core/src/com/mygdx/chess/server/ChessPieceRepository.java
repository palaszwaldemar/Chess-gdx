package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessPieceRepository {
    private final List<ChessPiece> chessPieces = new ArrayList<>();

    public ChessPieceRepository() {
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
    }

    Optional<ChessPiece> getChessPiece(int x, int y) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                return Optional.of(chessPiece);
            }
        }
        return Optional.empty();
    }

    List<ChessPiece> getChessPieces() {
        return chessPieces;
    }

    List<ChessPiece> getChessPieces(ChessPieceColor color) {
        List<ChessPiece> chessPiecesByColor = new ArrayList<>();
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.hasColor(color)) {
                chessPiecesByColor.add(chessPiece);
            }
        }
        return chessPiecesByColor;
    }

    List<ChessPiece> getChessPiecesAfterMove(ChessPiece chessPieceInUse, int x, int y) {
        List<ChessPiece> chessPiecesAfterMove = getDeepCopyOfChessPiece();
        chessPiecesAfterMove.removeIf(chessPiece -> chessPiece.getX() == x && chessPiece.getY() == y);
        for (ChessPiece chessPiece : chessPiecesAfterMove) {
            if (chessPieceInUse.equals(chessPiece)) {
                chessPiece.move(x, y);
            }
        }
        return chessPiecesAfterMove;
    }

    private List<ChessPiece> getDeepCopyOfChessPiece() {
        List<ChessPiece> deepCopyOfChessPiece = new ArrayList<>();
        for (ChessPiece chessPiece : chessPieces) {
            deepCopyOfChessPiece.add(chessPiece.clone());
        }
        return deepCopyOfChessPiece;
    }

    Optional<Rook> getRookByKingMove(int x, int y) {
        int xRook = x == 6 ? 7 : 0;
        return getRook(xRook, y);
    }

    private Optional<Rook> getRook(int x, int y) {
        if (getChessPiece(x, y).isEmpty()) {
            return Optional.empty();
        }
        ChessPiece chessPiece = getChessPiece(x, y).get();
        if (chessPiece.hasType(ChessPieceType.ROOK)) {
            return Optional.of((Rook) chessPiece);
        }
        return Optional.empty();
    }

    Optional<ChessPiece> getKing(List<ChessPiece> chessPieces, ChessPieceColor color) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.hasType(ChessPieceType.KING) && chessPiece.hasColor(color)) {
                return Optional.of(chessPiece);
            }
        }
        return Optional.empty();
    }
    // TODO: 04.01.2024 wrócić po przerobieniu Streamów
}
