package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

public class MoveReport { // CHECK : 19.10.2023 sprawdzić wszystkie użyte obiekty tego typu. Czy są poprawnie użyte, czy w dobrych miejscach
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
}
