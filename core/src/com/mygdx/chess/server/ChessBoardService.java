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
        moveReport.setChessPieceInUse(chessPieceInUse);
        capturePiece(endCordsVector, moveReport);
        moveRookBeforeKing(chessPieceInUse, endCordsVector, moveReport);
        chessPieceInUse.move(endCordsVector);
        pawnPromotion(chessPieceInUse, endCordsVector, moveReport);
        return moveReport;
    }

    private void capturePiece(CordsVector endCordsVector, MoveReport moveReport) {
        Iterator<ChessPiece> iterator = chessPieces.iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == endCordsVector.x &&
                    chessPieceToRemove.getY() == endCordsVector.y &&
                    !moveReport.getChessPieceInUse().hasSameColor(chessPieceToRemove)) {
                moveReport.setChessPieceToRemove(chessPieceToRemove);
                iterator.remove();
            }
        }
    }

    private void moveRookBeforeKing(ChessPiece chessPieceInUse, CordsVector endCordsVector,
                                    MoveReport moveReport) {
        if (isNotKingOrNotCastling(chessPieceInUse, endCordsVector)) {
            return;
        }
        // CHECK : 12.12.2023 osobna metoda? jak?
        boolean isRightCastling = endCordsVector.x == 6;
        int xRook = isRightCastling ? 7 : 0;
        int yRook = chessPieceInUse.getY();
        //
        moveRook(chessPieceInUse, xRook, yRook, moveReport);
    }

    // CHECK : 12.12.2023 wyodrębniono do osobnej metody
    private boolean isNotKingOrNotCastling(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        boolean isNotKingOrNotCastling = !chessPieceInUse.hasType(ChessPieceType.KING); // CHECK : 12.12.2023 czy wszędzie wykorzystać tą metodę?
        if (Math.abs(chessPieceInUse.getX() - endCordsVector.x) != 2) {
            isNotKingOrNotCastling = true;
        }
        return isNotKingOrNotCastling;
    }

    private void moveRook(ChessPiece rook, int xRook, int yRook, MoveReport moveReport) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.hasSameColor(rook) && chessPiece.getX() == xRook &&
                    chessPiece.getY() == yRook) {
                int newXRook = xRook == 7 ? 5 : 3; // CHECK : 12.12.2023 przekazywałem wcześniej
                //check tutaj isRightCastling. Czy tak jak teraz może byc?
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
        if (!chessPieceInUse.hasType(ChessPieceType.PAWN)) {
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
// Pobieranie ruchu | Opakowanie ruchu | Validacja | Wykonanie | Animowanie
// CHECK : 05.12.2023 na następnej lekcji prezbudować MoveReport
