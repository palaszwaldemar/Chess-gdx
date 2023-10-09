package com.mygdx.chess.server;

import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardService {
    private final List<ChessPiece> chessPieces = new ArrayList<>();
    private final MoveValidator moveValidator;

    public ChessBoardService() {
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
        moveValidator = new MoveValidator(chessPieces);
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }

    public void move(ChessPiece chessPieceInUse, float vectorX, float vectorY, int xEndPosition, int yEndPosition) throws InvalidMoveException {
        boolean isCanMove = moveValidator.isOnTheBoard(vectorX, vectorY) &&
                moveValidator.isNoSameColorPieceHere(chessPieceInUse.getColor(), xEndPosition, yEndPosition) &&
                moveValidator.isLineClear(chessPieceInUse, xEndPosition, yEndPosition) &&
                chessPieceInUse.isCorrectMovement(xEndPosition, yEndPosition);
        if (!isCanMove) {
            throw new InvalidMoveException();
        }
        chessPieceInUse.setPosition(xEndPosition, yEndPosition);
    }
}
