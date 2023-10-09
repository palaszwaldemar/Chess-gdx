package com.mygdx.chess.server;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.chess.client.GuiParams;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public boolean isOnTheBoard(Vector2 mouseDropPosition) {
        return mouseDropPosition.x >= 0 && mouseDropPosition.x <= GuiParams.CHESSBOARD_WIDTH && mouseDropPosition.y >= 0 && mouseDropPosition.y <= GuiParams.CHESSBOARD_HEIGHT;
    }

    boolean isNoSameColorPieceHere(ChessPieceColor color, EndCordsVector endCordsVector) {
        boolean isNoSameColorPieceHere = true;
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == endCordsVector.x && piece.getY() == endCordsVector.y) {
                if (piece.getColor().equals(color)) {
                    isNoSameColorPieceHere = false;
                }
            }
        }
        return isNoSameColorPieceHere;
    }

    boolean isLineClear(ChessPiece chessPieceInUse, EndCordsVector endCordsVector) {
        return checkLineByType(chessPieceInUse, endCordsVector);
    }

    private boolean checkLineByType(ChessPiece chessPieceInUse, EndCordsVector endCordsVector) {
        switch (chessPieceInUse.getType()) {
            case ROOK:
                if (chessPieceInUse.getX() == endCordsVector.x) {
                    return checkVertically(chessPieceInUse, endCordsVector.y);
                } else {
                    return checkHorizontally(chessPieceInUse, endCordsVector.x);
                }
            case RUNNER:
                return checkDiagonal(chessPieceInUse, endCordsVector.x, endCordsVector.y);
            case QUEEN:
                System.out.println("QUEEN");
                break;
        }
        return true;
    }

    private boolean checkVertically(ChessPiece chessPieceInUse, int yEndPosition) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == chessPieceInUse.getX()) {
                if (chessPieceInUse.getY() < yEndPosition) {
                    if (chessPiece.getY() > chessPieceInUse.getY() && chessPiece.getY() < yEndPosition) {
                        return false;
                    }
                } else {
                    if (chessPiece.getY() < chessPieceInUse.getY() && chessPiece.getY() > yEndPosition) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkHorizontally(ChessPiece chessPieceInUse, int xEndPosition) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getY() == chessPieceInUse.getY()) {
                if (chessPieceInUse.getX() < xEndPosition) {
                    if (chessPiece.getX() > chessPieceInUse.getX() && chessPiece.getX() < xEndPosition) {
                        return false;
                    }
                } else {
                    if (chessPiece.getX() < chessPieceInUse.getX() && chessPiece.getX() > xEndPosition) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkDiagonal(ChessPiece chessPieceInUse, int xEndPosition, int yEndPosition) {// TODO: 09.10.2023 nie działa lewy dół
        for (int x = (chessPieceInUse.getX() + 1), y = (chessPieceInUse.getY() + 1); x < xEndPosition && y < yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        for (int x = (chessPieceInUse.getX() + 1), y = (chessPieceInUse.getY() - 1); x < xEndPosition && y > yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        for (int x = (chessPieceInUse.getX() - 1), y = (chessPieceInUse.getY() + 1); x > xEndPosition && y < yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        for (int x = (chessPieceInUse.getX() - 1), y = (chessPieceInUse.getY() - 1); x > xEndPosition && y > yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }
}
