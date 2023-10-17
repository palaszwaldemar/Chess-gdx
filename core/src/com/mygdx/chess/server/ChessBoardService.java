package com.mygdx.chess.server;

import com.mygdx.chess.client.Controller;
import com.mygdx.chess.exceptions.InvalidMoveException;
import com.mygdx.chess.server.chessPieces.ChessPiece;
import com.mygdx.chess.server.chessPieces.Queen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChessBoardService {
    private final Controller controller; //todo remove
    private final MoveValidator moveValidator;
    private final List<ChessPiece> chessPieces = new ArrayList<>();

    public ChessBoardService(Controller controller) {
        this.controller = controller;
        PiecesFactory piecesFactory = new PiecesFactory();
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.WHITE));
        chessPieces.addAll(piecesFactory.getChessPieces(ChessPieceColor.BLACK));
        moveValidator = new MoveValidator(chessPieces);
    }

    public void move(ChessPiece chessPieceInUse, CordsVector endCordsVector) throws InvalidMoveException {// TODO: 17.10.2023 zwracać MoveRAport

        //podjąć decyzje jaki ruch
        //upewnic sie ze moge
        //ruszyć się
        //zbić
        //rozpatrzyć awans piona

        if (!moveValidator.isCanMove(chessPieceInUse, endCordsVector)) {
            throw new InvalidMoveException();
        }
        chessPieceInUse.setPosition(endCordsVector);
        pawnPromotion(chessPieceInUse, endCordsVector);
    }

    private void capturePiece(CordsVector endCordsVector) {
        Iterator<ChessPiece> iterator = chessPieces.iterator();
        while (iterator.hasNext()) {
            ChessPiece chessPieceToRemove = iterator.next();
            if (chessPieceToRemove.getX() == endCordsVector.x && chessPieceToRemove.getY() == endCordsVector.y) {
                iterator.remove();
                controller.removeActor(chessPieceToRemove); //todo czy da sie tego pozbyć?
            }
        }
    }

    private void pawnPromotion(ChessPiece chessPieceInUse, CordsVector endCordsVector) {
        if (chessPieceInUse.getType() == ChessPieceType.PAWN && (endCordsVector.y == 0 || endCordsVector.y == 7)) {// TODO: 11.10.2023 napisane na szybko
            changePawnToQueen(chessPieceInUse, endCordsVector);
        }
    }

    private void changePawnToQueen(ChessPiece chessPieceInUse, CordsVector endCordsVector) {// TODO: 11.10.2023 napisane na szybko
        capturePiece(endCordsVector);
        ChessPiece newQueen = new Queen(chessPieceInUse.getColor(), endCordsVector.x, endCordsVector.y);
        chessPieces.add(newQueen);
        controller.addChessPieceActor(newQueen);// TODO: 17.10.2023 czy da się usunąć
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
