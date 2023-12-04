package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

public class MoveReport {
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;
    private ChessPiece rookToMove;
    private int newXRook; // CHECK : 04.12.2023 znaleźć lepszy sposób?
    private int newYRook;

    public void setChessPieceToRemove(ChessPiece chessPieceToRemove) {
        this.chessPieceToRemove = chessPieceToRemove;
    }

    public void setPromotionPawnToRemove(ChessPiece promotionPawnToRemove) {
        this.promotionPawnToRemove = promotionPawnToRemove;
    }

    public void setPromotionTarget(ChessPiece promotionTarget) {
        this.promotionTarget = promotionTarget;
    }

    public void setRookToMove(ChessPiece rookToMove, int newXRook, int newYRook) {
        this.rookToMove = rookToMove;
        this.newXRook = newXRook * 100; // CHECK : 04.12.2023 GuiParams?
        this.newYRook = newYRook * 100;
    }

    public int getNewXRook() {
        return newXRook;
    }

    public int getNewYRook() {
        return newYRook;
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
