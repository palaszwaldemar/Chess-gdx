package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

public class MoveReport {
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;

    public void setChessPieceToRemove(ChessPiece chessPieceToRemove) {
        this.chessPieceToRemove = chessPieceToRemove;
    }

    public void setPromotionPawnToRemove(ChessPiece promotionPawnToRemove) {
        this.promotionPawnToRemove = promotionPawnToRemove;
    }

    public void setPromotionTarget(ChessPiece promotionTarget) {
        this.promotionTarget = promotionTarget;
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

    public boolean wasPromotion() {
        return promotionPawnToRemove != null;
    }
}
