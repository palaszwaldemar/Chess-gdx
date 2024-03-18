package com.mygdx.chess.server;

import java.util.List;

public class ServerFacade {
    private final ChessPieceRepository repository;
    private final MoveService moveService;
    private final ChessPieceFactory chessPieceFactory;

    public ServerFacade() {
        repository = new ChessPieceRepository();
        chessPieceFactory = new ChessPieceFactory();
        moveService = new MoveService(repository, chessPieceFactory);
        initBoard();
    }

    private void initBoard() {
        repository.addAll(chessPieceFactory.createStartingPieces(ChessPieceColor.WHITE));
        repository.addAll(chessPieceFactory.createStartingPieces(ChessPieceColor.BLACK));
    }

    public List<ChessPiece> getChessPieces(ChessPieceColor color) {
        return repository.getChessPieces(color);
    }

    public MoveReport move(MoveDto moveRequest) {
        MoveReport moveReport = moveService.move(moveRequest);
        if (moveReport.isValid()) {
            moveReport.changeActiveColor();
        }
        return moveReport;
    }

    public boolean isPromotion(MoveDto move) {
        return moveService.isPromotion(move);
    }
}
