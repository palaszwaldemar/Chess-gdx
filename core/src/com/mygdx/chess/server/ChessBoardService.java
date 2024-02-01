package com.mygdx.chess.server;

import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class ChessBoardService {
    private final ChessPieceRepository repository;
    private final MoveService moveService;
    private ChessPieceColor activeColor;

    public ChessBoardService() {
        repository = new ChessPieceRepository();
        moveService = new MoveService(repository);
        activeColor = ChessPieceColor.WHITE;
    }

    public List<ChessPiece> getChessPieces() {
        return repository.getChessPieces();
    }

    public MoveReport move(ChessPiece chessPieceInUse, int x, int y) {
        // TODO: 01.02.2024 ponowne sprawdzenie
        MoveReport moveReport = moveService.move(chessPieceInUse, x, y);
        if (moveReport.isValid()) {
            activeColor = chessPieceInUse.getColor().getEnemyColor();
        }
        return moveReport;
    }

    public ChessPieceColor whichColorTurn() {
        return activeColor;
    }
}
