package com.mygdx.chess.server;

import java.util.List;

import static com.mygdx.chess.server.ChessPieceColor.BLACK;
import static com.mygdx.chess.server.ChessPieceColor.WHITE;

/**
 * This class acts as a facade for the server-side logic of the chess game.
 * It encapsulates the interactions with the chess piece repository, move service, and chess piece factory.
 */
public class ServerFacade {
    private final ChessPieceRepository repository;
    private final MoveService moveService;
    private final ChessPieceFactory chessPieceFactory;

    /**
     * Constructor for the ServerFacade class.
     * Initializes the chess piece repository, move service, and chess piece factory.
     * Also initializes the chess board with starting pieces.
     */
    public ServerFacade() {
        repository = new ChessPieceRepository();
        chessPieceFactory = new ChessPieceFactory();
        moveService = new MoveService(repository, chessPieceFactory);
        initBoard();
    }

    /**
     * Initializes the chess board with starting pieces for both white and black.
     */
    private void initBoard() {
        repository.addAll(chessPieceFactory.createStartingPieces(WHITE));
        repository.addAll(chessPieceFactory.createStartingPieces(BLACK));
    }

    /**
     * Retrieves the current state of the chess game.
     *
     * @return A ChessGameDto object representing the current state of the game.
     */
    public ChessGameDto getChessGame() {
        List<ChessPiece> whiteChessPieces = repository.getChessPieces(WHITE);
        List<ChessPiece> blackChessPieces = repository.getChessPieces(BLACK);
        return new ChessGameDto(whiteChessPieces, blackChessPieces, moveService.getActiveColor());
    }

    /**
     * Executes a move in the chess game.
     *
     * @param moveRequest A MoveDto object representing the requested move.
     * @return A MoveReport object representing the result of the move.
     */
    public MoveReport move(MoveDto moveRequest) {
        return moveService.move(moveRequest);
    }

    /**
     * Checks if a move results in a promotion.
     *
     * @param move A MoveDto object representing the move to check.
     * @return A boolean indicating whether the move results in a promotion.
     */
    public boolean isPromotion(MoveDto move) {
        return moveService.isPromotion(move);
    }
}