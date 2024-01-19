package com.mygdx.chess.client;

import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.MoveReport;
import com.mygdx.chess.server.chessPieces.ChessPiece;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessboardGroup chessboardGroup;

    public Controller(ChessboardGroup chessboardGroup) {
        this.chessboardGroup = chessboardGroup;
    }

    void startGame() {
        chessboardGroup.createActors(service.getChessPieces());
    }

    void move(ChessPiece chessPieceInUse, int x, int y) throws InvalidMoveException {
        MoveReport moveReport = service.move(chessPieceInUse, x, y);
        removeActor(moveReport.getChessPieceToRemove());
        castling(moveReport);
        if (moveReport.wasPromotion()) {
            replaceChessPieceActor(moveReport);
        }
    }

    private void removeActor(ChessPiece chessPieceToRemove) {
        SnapshotArray<ChessPieceActor> chessPieceActors = chessboardGroup.getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(chessPieceToRemove)) {
                chessboardGroup.removeActor(chessPieceActor);
            }
        }
    }

    private void castling(MoveReport moveReport) {
        SnapshotArray<ChessPieceActor> chessPieceActors = chessboardGroup.getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(moveReport.getRookToMove())) {
                chessPieceActor.setPosition(moveReport.getNewXRook(), moveReport.getNewYRook());
            }
        }
    }

    private void replaceChessPieceActor(MoveReport moveReport) {
        removeActor(moveReport.getPromotionPawnToRemove());
        chessboardGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }
}
