package com.mygdx.chess.server;

import java.util.List;

public class ServerFacade {
    private final ChessPieceRepository repository;
    private final MoveService moveService;
    private final ChessPieceFactory chessPieceFactory;
    private ChessPieceColor activeColor;

    public ServerFacade() {
        repository = new ChessPieceRepository();
        chessPieceFactory = new ChessPieceFactory();
        moveService = new MoveService(repository, chessPieceFactory);
        activeColor = ChessPieceColor.WHITE;
        initBoard();
    }

    private void initBoard() {
        repository.addAll(chessPieceFactory.createStartingPieces(ChessPieceColor.WHITE));
        repository.addAll(chessPieceFactory.createStartingPieces(ChessPieceColor.BLACK));
    }

    public List<ChessPiece> getChessPieces() {
        return repository.getChessPieces();
    }

    public MoveReport move(MoveDto moveRequest) {
        MoveReport moveReport = moveService.move(moveRequest);
        //todo report
        if (moveReport.isValid()) {
            activeColor = moveRequest.inUse().getColor().getEnemyColor();
        }
        return moveReport;
    }

    public boolean isPromotion(MoveDto move) {
        return moveService.isPromotion(move);
    }
}
