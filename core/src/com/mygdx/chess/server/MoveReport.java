package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Rook;

public class MoveReport {
    private ChessPiece chessPieceInUse;
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;
    private Rook rookToMove;
    // CHECK : 23.01.2024 usunąłem int newXRook, newYRook, zostawiłem rookToMove i z tego obiektu korzystam do
    //check przesuwania figurą w Controller

    public void setChessPieceInUse(ChessPiece chessPieceInUse) {
        this.chessPieceInUse = chessPieceInUse;
    }

    public void setChessPieceToRemove(ChessPiece chessPieceToRemove) {
        this.chessPieceToRemove = chessPieceToRemove;
    }

    public void setPromotionPawnToRemove(ChessPiece promotionPawnToRemove) {
        this.promotionPawnToRemove = promotionPawnToRemove;
    }

    public void setPromotionTarget(ChessPiece promotionTarget) {
        this.promotionTarget = promotionTarget;
    }

    public void setRookToMove(Rook rookToMove) {
        this.rookToMove = rookToMove;
    }

    public int getNewXRook() { // CHECK : 24.01.2024 czy tak może być?
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
}
