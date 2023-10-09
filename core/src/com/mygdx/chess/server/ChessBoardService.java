package com.mygdx.chess.server;

import com.badlogic.gdx.math.Vector2;
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

    public void move(ChessPiece chessPieceInUse, EndCordsVector endCordsVector, Vector2 mouseDropPosition) throws InvalidMoveException {
        boolean isCanMove = moveValidator.isOnTheBoard(mouseDropPosition) &&
                moveValidator.isNoSameColorPieceHere(chessPieceInUse.getColor(), endCordsVector) &&
                moveValidator.isClearLine(chessPieceInUse, endCordsVector) &&
                chessPieceInUse.isCorrectMovement(endCordsVector);
        if (!isCanMove) {
            throw new InvalidMoveException();
        }
        chessPieceInUse.setPosition(endCordsVector);
    }
}
