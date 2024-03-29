package com.mygdx.chess.server;

import java.util.Iterator;
import java.util.Optional;

class MoveService {
    private final MoveValidator moveValidator;
    private final ChessPieceRepository repository;
    private final ChessPieceFactory chessPieceFactory;

    private MoveDto move;
    private  MoveReport moveReport;

    MoveService(ChessPieceRepository repository, ChessPieceFactory chessPieceFactory) {
        this.repository = repository;
        this.moveValidator = new MoveValidator(repository);
        this.chessPieceFactory = chessPieceFactory;
    }

    MoveReport move(MoveDto move) {
       this.move = move;
        moveReport = new MoveReport(move.inUse());
        if ( !moveValidator.canMove(move)) {
            return moveReport;
        }
        moveReport.setValid();
        capturePiece();
        moveRookBeforeKing();
        move.inUse().move(move.x(), move.y()); //todo ?
        pawnPromotion();
        return moveReport;
    }

    private void capturePiece() {
        Iterator<ChessPiece> iterator = repository.getChessPieces().iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == move.x() && chessPieceToRemove.getY() == move.y() &&
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
        Optional<Rook> rookToMove = repository.getRookByKingMove(move.x(), move.inUse().getY());
        if (rookToMove.isPresent()) {
            Rook rook = rookToMove.get();
            moveRook(rook);
        }
    }

    private boolean isNotKingOrNotCastling() {
        boolean isNotKingOrNotCastling = !move.inUse().hasType(ChessPieceType.KING);
        if (Math.abs(move.inUse().getX() - move.x()) != 2) {
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
        if (!move.inUse().hasType(ChessPieceType.PAWN)) {
            return;
        }
        if (move.y() == 0 || move.y() == 7) {
            moveReport.setPromotionPawnToRemove();
            changePawnToFigure();
        }
    }

    private void changePawnToFigure() {
        ChessPiece newFigure = chessPieceFactory.createPromotionFigure(move);
        repository.remove(move.inUse());
        repository.add(newFigure);
        moveReport.setPromotionTarget(newFigure);
    }

    boolean isPromotion(MoveDto moveDto) {
        return moveDto.inUse().hasType(ChessPieceType.PAWN) && (moveDto.y() == 0 || moveDto.y() == 7) &&
            moveValidator.canMove(moveDto);
    }
}
