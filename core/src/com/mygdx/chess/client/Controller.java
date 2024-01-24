package com.mygdx.chess.client;

import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.ChessBoardService;
import com.mygdx.chess.server.MoveReport;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class Controller {
    private final ChessBoardService service = new ChessBoardService();
    private final ChessPieceGroup chessPieceGroup;

    public Controller(ChessPieceGroup chessPieceGroup) {
        this.chessPieceGroup = chessPieceGroup;
    }

    void startGame() {
        chessPieceGroup.createActors(service.getChessPieces());
    }

    void move(ChessPiece chessPieceInUse, int x, int y) throws InvalidMoveException {
        MoveReport moveReport = service.move(chessPieceInUse, x, y);
        chessPieceGroup.removeActorBy(moveReport.getChessPieceToRemove());
        castling(moveReport);
        if (moveReport.wasPromotion()) {
            replaceChessPieceActor(moveReport);
        }
    }

    private void castling(MoveReport moveReport) {
        List<ChessPieceActor> chessPieceActors = chessPieceGroup.getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(moveReport.getRookToMove())) {
                chessPieceActor.setPosition(moveReport.getNewXRook(), moveReport.getNewYRook());
            }
        }
    }

    private void replaceChessPieceActor(MoveReport moveReport) {
        chessPieceGroup.removeActorBy(moveReport.getPromotionPawnToRemove());
        chessPieceGroup.addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }
}
