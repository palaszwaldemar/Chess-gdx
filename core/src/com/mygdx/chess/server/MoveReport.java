package com.mygdx.chess.server;

public class MoveReport {
    private final ChessPiece chessPieceInUse;
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;
    private Rook rookToMove;
    private boolean valid;

    MoveReport(ChessPiece chessPieceInUse) {
        this.chessPieceInUse = chessPieceInUse;
    }

    void setChessPieceToRemove(ChessPiece chessPieceToRemove) {
        this.chessPieceToRemove = chessPieceToRemove;
    }

    void setPromotionPawnToRemove(ChessPiece promotionPawnToRemove) {
        this.promotionPawnToRemove = promotionPawnToRemove;
    }

    public void setPromotionTarget(ChessPiece promotionTarget) {// TODO: 08.02.2024
        this.promotionTarget = promotionTarget;
    }

    void setRookToMove(Rook rookToMove) {
        this.rookToMove = rookToMove;
    }

    void setValid() {
        valid = true;
    }

    public int getNewXRook() {
        return chessPieceInUse.getX() == 6 ? 5 : 3;
    }

    public int getNewYRook() {
        return rookToMove.getY();
    }

    public ChessPiece getChessPieceInUse() {
        return chessPieceInUse;
    }

    public ChessPiece getChessPieceToRemove() {
        return chessPieceToRemove;
    }

    public ChessPiece getPromotionPawnToRemove() {
        return promotionPawnToRemove;
    }

    public ChessPiece getPromotionTarget() {
        return promotionTarget;
    }

    public ChessPiece getRookToMove() {
        return rookToMove;
    }

    public boolean wasPromotion() {
        return promotionPawnToRemove != null;
    }

    public boolean isValid() {
        return valid;
    }
}
