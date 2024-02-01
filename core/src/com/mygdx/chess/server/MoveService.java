package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Queen;
import com.mygdx.chess.server.chessPieces.Rook;

import java.util.Iterator;
import java.util.Optional;

public class MoveService {
    private final MoveValidator moveValidator;
    private final ChessPieceRepository repository;
    private MoveReport moveReport;
    private ChessPiece chessPieceInUse;
    private int x;
    private int y;

    public MoveService(ChessPieceRepository repository) {
        this.repository = repository;
        this.moveValidator = new MoveValidator(repository);
    }

    public MoveReport move(ChessPiece chessPieceInUse, int x, int y) {
        this.chessPieceInUse = chessPieceInUse;
        this.x = x;
        this.y = y;
        moveReport = new MoveReport(chessPieceInUse);
        if (!moveReport.isValid() && !moveValidator.canMove(chessPieceInUse, x, y)) {
            return moveReport;
        }
        moveReport.setValid(true);
        capturePiece();
        moveRookBeforeKing();
        chessPieceInUse.move(x, y);
        pawnPromotion();
        return moveReport;
    }

    private void capturePiece() {
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

    private void moveRookBeforeKing() {
        if (isNotKingOrNotCastling()) {
            return;
        }
        Optional<Rook> rookToMove = repository.getRookByKingMove(x, chessPieceInUse.getY());
        if (rookToMove.isPresent()) {
            Rook rook = rookToMove.get();
            moveRook(rook);
        }
    }

    private boolean isNotKingOrNotCastling() {
        boolean isNotKingOrNotCastling = !chessPieceInUse.hasType(ChessPieceType.KING);
        if (Math.abs(chessPieceInUse.getX() - x) != 2) {
            isNotKingOrNotCastling = true;
        }
        return isNotKingOrNotCastling;
    }

    private void moveRook(Rook rookToMove) {
        int newXRook = rookToMove.getCastlingX();
        moveReport.setRookToMove(rookToMove);
        rookToMove.move(newXRook, rookToMove.getY());
    }

    private void pawnPromotion() {
        if (!chessPieceInUse.hasType(ChessPieceType.PAWN)) {
            return;
        }
        if (y == 0 || y == 7) {
            moveReport.setPromotionPawnToRemove(chessPieceInUse);
            changePawnToQueen();
        }
    }

    private void changePawnToQueen() {
        repository.getChessPieces().remove(chessPieceInUse);
        ChessPiece newQueen = new Queen(chessPieceInUse.getColor(), x, y);
        repository.getChessPieces().add(newQueen);
        moveReport.setPromotionTarget(newQueen);
    }
}
