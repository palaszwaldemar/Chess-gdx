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

    public ChessGameDto getChessGame() {
        List<ChessPiece> whiteChessPieces = repository.getChessPieces(ChessPieceColor.WHITE);
        List<ChessPiece> blackChessPieces = repository.getChessPieces(ChessPieceColor.BLACK);
        return new ChessGameDto(whiteChessPieces, blackChessPieces, moveService.getActiveColor());
    }

    public MoveReport move(MoveDto moveRequest) {
        return moveService.move(moveRequest);
    }

    public boolean isPromotion(MoveDto move) {
        return moveService.isPromotion(move);
    }
}
