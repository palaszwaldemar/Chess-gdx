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

    public MoveReport move(ChessPiece chessPieceInUse, CordsVector endCordsVector) throws InvalidMoveException {
        if (!moveValidator.isCanMove(chessPieceInUse, endCordsVector)) {
            throw new InvalidMoveException();
        }
        MoveReport moveReport = capturePiece(endCordsVector);
        chessPieceInUse.setPosition(endCordsVector);
        pawnPromotion(chessPieceInUse, endCordsVector, moveReport);
        return moveReport;
    }

    private MoveReport capturePiece(CordsVector endCordsVector) {
        MoveReport moveReport = new MoveReport();
        Iterator<ChessPiece> iterator = chessPieces.iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == endCordsVector.x && chessPieceToRemove.getY() == endCordsVector.y) {
                moveReport.setChessPieceToRemove(chessPieceToRemove);
                iterator.remove();
            }
        }
        return moveReport;
    }

    private void pawnPromotion(ChessPiece chessPieceInUse, CordsVector endCordsVector, MoveReport moveReport) {
        if (chessPieceInUse.getType() == ChessPieceType.PAWN && (endCordsVector.y == 0 || endCordsVector.y == 7)) {
            moveReport.setPromotionPawnToRemove(chessPieceInUse);
            changePawnToQueen(chessPieceInUse, endCordsVector, moveReport);
        }
    }

    private void changePawnToQueen(ChessPiece chessPieceInUse, CordsVector endCordsVector, MoveReport moveReport) {
        capturePiece(endCordsVector);
        ChessPiece newQueen = new Queen(chessPieceInUse.getColor(), endCordsVector.x, endCordsVector.y);
        chessPieces.add(newQueen);
        moveReport.setPromotionTarget(newQueen);
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
