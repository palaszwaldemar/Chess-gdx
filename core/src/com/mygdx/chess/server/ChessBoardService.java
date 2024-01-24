package com.mygdx.chess.server;

import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Queen;
import com.mygdx.chess.server.chessPieces.Rook;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ChessBoardService {
    private final ChessPieceRepository repository;
    private final MoveValidator moveValidator;

    public ChessBoardService() {
        repository = new ChessPieceRepository();
        moveValidator = new MoveValidator(repository);
    }

    public List<ChessPiece> getChessPieces() {
        return repository.getChessPieces();
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
        Optional<Rook> rookToMove = repository.getRookByKingMove(x, chessPieceInUse.getY());
        if (rookToMove.isPresent()) {
            Rook rook = rookToMove.get();
            moveRook(rook, moveReport);
        }
    }

    private boolean isNotKingOrNotCastling(ChessPiece chessPieceInUse, int x) {
        boolean isNotKingOrNotCastling = !chessPieceInUse.hasType(ChessPieceType.KING);
        if (Math.abs(chessPieceInUse.getX() - x) != 2) {
            isNotKingOrNotCastling = true;
        }
        return isNotKingOrNotCastling;
    }

    private void moveRook(Rook rookToMove, MoveReport moveReport) {
        int newXRook = rookToMove.getCastlingX();
        moveReport.setRookToMove(rookToMove);
        rookToMove.move(newXRook, rookToMove.getY());
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
}
// Pobieranie ruchu | Opakowanie ruchu | Walidacja | Wykonanie | Animowanie
// CHECK : 11.01.2024 dodatkowa klasa - podserwis - przygotowuje wszystko do ruchu
