package com.mygdx.chess.server;

import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Queen;

import java.util.Iterator;
import java.util.List;

public class ChessBoardService {
    private final ChessPieceRepository repository;
    private final MoveValidator moveValidator;

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        repository = new ChessPieceRepository(piecesFactory);
        moveValidator = new MoveValidator(repository);
    }

    public MoveReport move(ChessPiece chessPieceInUse, int x, int y) throws InvalidMoveException {
        if (!moveValidator.canMove(chessPieceInUse, x, y)) {
            throw new InvalidMoveException();
        }
        MoveReport moveReport = new MoveReport();
        moveReport.setChessPieceInUse(chessPieceInUse);
        capturePiece(x, y, moveReport);
        moveRookBeforeKing(chessPieceInUse, x, moveReport);
        chessPieceInUse.move(x, y);
        pawnPromotion(chessPieceInUse, x, y, moveReport);
        return moveReport;
    }

    private void capturePiece(int x, int y, MoveReport moveReport) {
        Iterator<ChessPiece> iterator = repository.getChessPieces().iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == x && chessPieceToRemove.getY() == y &&
                !moveReport.getChessPieceInUse().hasSameColor(chessPieceToRemove)) {
                moveReport.setChessPieceToRemove(chessPieceToRemove);
                iterator.remove();
            }
        }
    }

    private void moveRookBeforeKing(ChessPiece chessPieceInUse, int x, MoveReport moveReport) {
        if (isNotKingOrNotCastling(chessPieceInUse, x)) {
            return;
        }
        // CHECK : 12.12.2023 osobna metoda? jak?
        boolean isRightCastling = x == 6;
        int xRook = isRightCastling ? 7 : 0;
        int yRook = chessPieceInUse.getY();
        //
        moveRook(chessPieceInUse, xRook, yRook, moveReport);
    }

    // CHECK : 12.12.2023 wyodrębniono do osobnej metody
    private boolean isNotKingOrNotCastling(ChessPiece chessPieceInUse, int x) {
        boolean isNotKingOrNotCastling = !chessPieceInUse.hasType(ChessPieceType.KING);
        if (Math.abs(chessPieceInUse.getX() - x) != 2) {
            isNotKingOrNotCastling = true;
        }
        return isNotKingOrNotCastling;
    }

    private void moveRook(ChessPiece rook, int xRook, int yRook, MoveReport moveReport) {
        ChessPieceColor color = rook.getColor();
        List<ChessPiece> rooksByColor = repository.getRooksByColor(color);
        for (ChessPiece chessPiece : rooksByColor) {
            if (chessPiece.getX() == xRook && chessPiece.getY() == yRook) {
                int newXRook = xRook == 7 ? 5 : 3; // CHECK : 12.12.2023 przekazywałem wcześniej check tutaj isRightCastling. Czy tak jak teraz może byc?
                moveReport.setRookToMove(chessPiece, newXRook, yRook);
                setRookPosition(chessPiece, newXRook, yRook);
            }
        }
    }

    private void setRookPosition(ChessPiece rook, int x, int y) {
        rook.move(x, y);
    }

    private void pawnPromotion(ChessPiece chessPieceInUse, int x, int y, MoveReport moveReport) {
        if (!chessPieceInUse.hasType(ChessPieceType.PAWN)) {
            return;
        }
        if (y == 0 || y == 7) {
            moveReport.setPromotionPawnToRemove(chessPieceInUse);
            changePawnToQueen(chessPieceInUse, x, y, moveReport);
        }
    }

    private void changePawnToQueen(ChessPiece chessPieceInUse, int x, int y, MoveReport moveReport) {
        repository.getChessPieces().remove(chessPieceInUse);
        ChessPiece newQueen = new Queen(chessPieceInUse.getColor(), x, y);
        repository.getChessPieces().add(newQueen);
        moveReport.setPromotionTarget(newQueen);
    }

    public List<ChessPiece> getChessPieces() {
        return repository.getChessPieces();
    }
}
// Pobieranie ruchu | Opakowanie ruchu | Walidacja | Wykonanie | Animowanie
// CHECK : 05.12.2023 na następnej lekcji prezbudować MoveReport. Czy muszę przebudowywać MoveReport? Czy teraz jest bardzo źle?
