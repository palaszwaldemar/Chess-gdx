package com.mygdx.chess.server;

public class MoveReport {
    private final ChessPiece chessPieceInUse;
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;
    private Rook rookToMove;
    private final ChessPieceColor activeColor;
    private boolean isStalemate = false;
    private boolean isCheckMate = false;
    private boolean valid;

    public MoveReport(ChessPiece chessPieceInUse) {
        this.chessPieceInUse = chessPieceInUse;
        activeColor = chessPieceInUse.getColor();
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

    void setValid() {
        valid = true;
    }

    void setStalemate() {
        isStalemate = true;
    }

    void setCheckMate() {
        isCheckMate = true;
    }

    public ChessPieceColor getActiveColor() {
        return activeColor;
    }

    public ChessPieceColor getNextColor() {
        return activeColor == ChessPieceColor.WHITE ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
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

    public boolean isStalemate() {
        return isStalemate;
    }

    public boolean isCheckMate() {
        return isCheckMate;
    }
}
