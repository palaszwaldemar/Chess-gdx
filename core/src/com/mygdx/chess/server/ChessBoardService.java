package com.mygdx.chess.server;

import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Queen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChessBoardService {
    private final MoveValidator moveValidator;
    private final List<ChessPiece> chessPieces = new ArrayList<>();

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
        moveValidator = new MoveValidator(chessPieces);
    }

    public MoveReport move(ChessPiece chessPieceInUse, CordsVector endCordsVector)
            throws InvalidMoveException {
        if (!moveValidator.canMove(chessPieceInUse, endCordsVector)) {
            throw new InvalidMoveException();
        }
        MoveReport moveReport = new MoveReport();
        // CHECK : 04.12.2023 kolejność tych 4 metod ma znaczenie. Czy to błąd?
        capturePiece(endCordsVector, moveReport);
        castling(chessPieceInUse, endCordsVector, moveReport);
        chessPieceInUse.move(endCordsVector);
        pawnPromotion(chessPieceInUse, endCordsVector, moveReport);
        //
        return moveReport;
    }

    private void capturePiece(CordsVector endCordsVector, MoveReport moveReport) {
        Iterator<ChessPiece> iterator = chessPieces.iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == endCordsVector.x &&
                    chessPieceToRemove.getY() == endCordsVector.y) {
                moveReport.setChessPieceToRemove(chessPieceToRemove);
                iterator.remove();
            }
        }
    }

    private void castling(ChessPiece king, CordsVector endCordsVector, MoveReport moveReport) {
        if (king.getType() != ChessPieceType.KING) {
            return;
        }
        if (Math.abs(king.getX() - endCordsVector.x) != 2) {
            return;
        }
        boolean isRightCastling = endCordsVector.x == 6;
        int xRook = isRightCastling ? 7 : 0;
        int yRook = king.getY();
        moveRook(king.getColor(), xRook, yRook, isRightCastling, moveReport);
    }

    private void moveRook(ChessPieceColor color, int xRook, int yRook,
                          boolean isRightCastling, MoveReport moveReport) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getColor() == color && chessPiece.getX() == xRook &&
                    chessPiece.getY() == yRook) {
                int newXRook = isRightCastling ? 5 : 3;
                moveReport.setRookToMove(chessPiece, newXRook, yRook);
                setRookPosition(chessPiece, newXRook, yRook);
            }
        }
    }

    private void setRookPosition(ChessPiece rook, int x, int y) {
        CordsVector endCordsVector = new CordsVector(x, y);
        rook.move(endCordsVector);
    }

    private void pawnPromotion(ChessPiece chessPieceInUse, CordsVector endCordsVector,
                               MoveReport moveReport) {
        if (chessPieceInUse.getType() != ChessPieceType.PAWN) {
            return;
        }
        if (endCordsVector.y == 0 || endCordsVector.y == 7) {
            moveReport.setPromotionPawnToRemove(chessPieceInUse);
            changePawnToQueen(chessPieceInUse, endCordsVector, moveReport);
        }
    }

    private void changePawnToQueen(ChessPiece chessPieceInUse, CordsVector endCordsVector,
                                   MoveReport moveReport) {
        chessPieces.remove(chessPieceInUse);
        ChessPiece newQueen =
                new Queen(chessPieceInUse.getColor(), endCordsVector.x, endCordsVector.y);
        chessPieces.add(newQueen);
        moveReport.setPromotionTarget(newQueen);
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
