package com.mygdx.chess.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.chess.server.*;

import java.util.List;

/**
 * This class represents the controller for the chess game.
 * It manages the game flow and interactions between the server and the client.
 */
class Controller {
    private final ServerFacade service = new ServerFacade();
    private final List<PlayerGroup> players;
    private final Stage stage;
    private MoveReport moveReport;

    /**
     * Constructor for the Controller class.
     *
     * @param stage   The stage where the game is displayed.
     * @param players The list of players in the game.
     */
    Controller(Stage stage, List<PlayerGroup> players) {
        this.players = players;
        this.stage = stage;
    }

    /**
     * Starts the game by getting the initial game state from the server and setting up the players.
     */
    void startGame() {
        ChessGameDto chessGameDto = service.getChessGame();
        PlayerGroup friendPlayer = getPlayerByColor(chessGameDto.activeColor());
        PlayerGroup enemyPlayer = getPlayerByColor(chessGameDto.activeColor().getSecondColor());
        friendPlayer.createActors(chessGameDto.whiteChessPieces());
        enemyPlayer.createActors(chessGameDto.blackChessPieces());
        enemyPlayer.setTouchable(Touchable.disabled);
    }

    /**
     * Handles a move request from a player.
     *
     * @param move The move to be made.
     * @return true if the move leads to a promotion, false otherwise.
     */
    boolean move(MoveDto move) {
        if (service.isPromotion(move)) {
            showPromotionWindow(move);
            return true;
        } else {
            return continueMove(move);
        }
    }

    /**
     * Displays a promotion window when a pawn reaches the opposite end of the board.
     *
     * @param move The move leading to the promotion.
     */
    private void showPromotionWindow(MoveDto move) {
        PromotionWindow promotionWindow = new PromotionWindow(move, this);
        stage.addActor(promotionWindow);
        lockChessboard(true);
    }

    /**
     * Continues the move if it does not lead to a promotion.
     *
     * @param move The move to be made.
     * @return true if the move is valid, false otherwise.
     */
    boolean continueMove(MoveDto move) {
        MoveReport moveReport = service.move(move);
        if (!moveReport.isValid()) {
            return false;
        }
        this.moveReport = moveReport;
        if (moveReport.isStalemate() || moveReport.isCheckMate()) {
            showEndWindow(moveReport);
            return true;
        }
        lastPlayerEnemy().removeActor(moveReport.getChessPieceToRemove());
        castling(moveReport);
        promotion(moveReport);
        switchPlayer();
        return moveReport.isValid();
    }

    /**
     * Displays an end window when the game reaches a stalemate or checkmate.
     *
     * @param moveReport The report of the move leading to the end of the game.
     */
    private void showEndWindow(MoveReport moveReport) {
        EndWindow endWindow = new EndWindow(moveReport, this);
        stage.addActor(endWindow);
        lockChessboard(true);
    }

    /**
     * Switches the active player after a move.
     */
    private void switchPlayer() {
        for (PlayerGroup player : players) {
            if (player.getChessPieceColor() == moveReport.getNextColor()) {
                player.setTouchable(Touchable.enabled);
            } else {
                player.setTouchable(Touchable.disabled);
            }
        }
    }

    /**
     * Handles the castling move if applicable.
     *
     * @param moveReport The report of the move.
     */
    private void castling(MoveReport moveReport) {
        List<ChessPieceActor> chessPieceActors = lastPlayer().getChessPieceActors();
        for (ChessPieceActor chessPieceActor : chessPieceActors) {
            if (chessPieceActor.getChessPiece().equals(moveReport.getRookToMove())) {
                float newXRook = Cords.xToPixels(moveReport.getNewXRook());
                float newYRook = Cords.yToPixels(moveReport.getNewYRook());
                chessPieceActor.setPosition(newXRook, newYRook);
            }
        }
    }

    /**
     * Locks or unlocks the chessboard.
     *
     * @param enable If true, the chessboard is locked. If false, the chessboard is unlocked.
     */
    void lockChessboard(boolean enable) {
        for (PlayerGroup player : players) {
            player.setTouchable(enable ? Touchable.disabled : Touchable.enabled);
        }
    }

    /**
     * Handles the promotion of a pawn.
     *
     * @param moveReport The report of the move leading to the promotion.
     */
    private void promotion(MoveReport moveReport) {
        if (!moveReport.wasPromotion()) {
            return;
        }
        lockChessboard(false);
        lastPlayer().removeActor(moveReport.getPromotionPawnToRemove());
        lastPlayer().addActor(new ChessPieceActor(moveReport.getPromotionTarget(), this));
    }

    /**
     * Removes an actor from the stage.
     *
     * @param actor The actor to be removed.
     */
    void removeActorFromStage(Actor actor) {
        stage.getRoot().removeActor(actor);
    }

    /**
     * Gets a player by their color.
     *
     * @param color The color of the player.
     * @return The player with the specified color.
     */
    private PlayerGroup getPlayerByColor(ChessPieceColor color) {
        for (PlayerGroup player : players) {
            if (player.getChessPieceColor() == color) {
                return player;
            }
        }
        throw new IllegalStateException("no player of matching color");
    }

    /**
     * Gets the last player who made a move.
     *
     * @return The last player who made a move.
     */
    private PlayerGroup lastPlayer() {
        return getPlayerByColor(moveReport.getActiveColor());
    }

    /**
     * Gets the enemy of the last player who made a move.
     *
     * @return The enemy of the last player who made a move.
     */
    private PlayerGroup lastPlayerEnemy() {
        return getPlayerByColor(moveReport.getNextColor());
    }
}