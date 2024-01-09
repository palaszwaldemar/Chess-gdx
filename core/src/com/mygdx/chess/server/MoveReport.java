package com.mygdx.chess.server;
// CHECK : 13.12.2023 czy MoveReport powinien być w tym pakiecie?
import com.mygdx.chess.client.Cords;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class MoveReport {
    private ChessPiece chessPieceInUse;
    private ChessPiece chessPieceToRemove;
    private ChessPiece promotionPawnToRemove;
    private ChessPiece promotionTarget;
    private ChessPiece rookToMove;
    // CHECK : 05.12.2023 na następnej lekcji
    private int newXRook; // CHECK : 04.12.2023 znaleźć lepszy sposób? Zamiast newXRook i newYRook, poprostu obiekt Rook?
    private int newYRook;

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

    public void setRookToMove(ChessPiece rookToMove, int newXRook, int newYRook) {
        this.rookToMove = rookToMove;
        this.newXRook = Cords.xToPixels(newXRook);
        this.newYRook = Cords.yToPixels(newYRook);
    }

    public ChessPiece getChessPieceInUse() {
        return chessPieceInUse;
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
