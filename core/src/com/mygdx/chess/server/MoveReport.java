package com.mygdx.chess.server;

public class MoveReport {
    private final ChessPiece chessPieceInUse;
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;
    private Rook rookToMove;
    private ChessPieceColor nextActiveColor;
    private boolean valid;

    public MoveReport(ChessPiece chessPieceInUse) {
        this.chessPieceInUse = chessPieceInUse;
        nextActiveColor = actualColor();
    }

    void setChessPieceToRemove(ChessPiece chessPieceToRemove) {
        this.chessPieceToRemove = chessPieceToRemove;
    }

    void setPromotionPawnToRemove() {
        this.promotionPawnToRemove = chessPieceInUse;
    }

    void setPromotionTarget(ChessPiece promotionTarget) {
        this.promotionTarget = promotionTarget;
    }

    void setRookToMove(Rook rookToMove) {
        this.rookToMove = rookToMove;
    }

    void changeActiveColor() {
        nextActiveColor = nextActiveColor == ChessPieceColor.WHITE ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
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

    public ChessPieceColor getNextActiveColor() {
        return nextActiveColor;
    }

    public boolean isValid() {
        return valid;
    }

    public ChessPieceColor actualColor() {
        return chessPieceInUse.getColor();
    }
}
