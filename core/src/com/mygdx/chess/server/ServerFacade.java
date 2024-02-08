package com.mygdx.chess.server;

import java.util.List;

public class ServerFacade {
    private final ChessPieceRepository repository;
    private final MoveService moveService;
    private ChessPieceColor activeColor;

    public ServerFacade() {
        repository = new ChessPieceRepository();
        moveService = new MoveService(repository);
        activeColor = ChessPieceColor.WHITE;
    }

    public List<ChessPiece> getChessPieces() {
        return repository.getChessPieces();
    }

    public MoveReport move(ChessPiece chessPieceInUse, int x, int y) {
        // TODO: 01.02.2024 ponowne sprawdzenie
        // CHECK : 07.02.2024 co jest do ponownego sprawdzenia?
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
