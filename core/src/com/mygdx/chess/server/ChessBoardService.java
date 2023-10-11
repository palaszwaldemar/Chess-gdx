package com.mygdx.chess.server;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.chess.client.Controller;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChessBoardService {
    private final Controller controller;
    private final List<ChessPiece> chessPieces = new ArrayList<>();
    private final MoveValidator moveValidator;

    public ChessBoardService(Controller controller) {
        this.controller = controller;
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
                moveValidator.isClearLineOrCorrectPawnMove(chessPieceInUse, endCordsVector) &&
                chessPieceInUse.isCorrectMovement(endCordsVector);
        if (!isCanMove) {
            throw new InvalidMoveException();
        }
        capturePiece(endCordsVector);
        chessPieceInUse.setPosition(endCordsVector);
    }

    private void capturePiece(EndCordsVector endCordsVector) {
        Iterator<ChessPiece> iterator = chessPieces.iterator();
        while (iterator.hasNext()) {
            ChessPiece element = iterator.next();
            if (element.getX() == endCordsVector.x && element.getY() == endCordsVector.y) {
                iterator.remove();
                controller.removeActor(endCordsVector);
            }
        }
    }
}
