package com.mygdx.chess.server;

import com.mygdx.chess.client.GuiParams;
import com.mygdx.chess.server.chessPieces.ChessPiece;

import java.util.List;

public class MoveValidator {
    private final List<ChessPiece> chessPieces;

    public MoveValidator(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public boolean isOnTheBoard(float vectorX, float vectorY) {
        return vectorX >= 0 && vectorX <= GuiParams.CHESSBOARD_WIDTH && vectorY >= 0 && vectorY <= GuiParams.CHESSBOARD_HEIGHT;
    }

    boolean isNoSameColorPieceHere(ChessPieceColor color, int xEndPosition, int yEndPosition) {
        boolean isNoSameColorPieceHere = true;
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == xEndPosition && piece.getY() == yEndPosition) {
                if (piece.getColor().equals(color)) {
                    isNoSameColorPieceHere = false;
                }
            }
        }
        return isNoSameColorPieceHere;
    }

    boolean isLineClear(ChessPiece chessPieceInUse, int xEndPosition, int yEndPosition) {
        return checkLineByType(chessPieceInUse.getType(), chessPieceInUse.getX(),
                chessPieceInUse.getY(), xEndPosition, yEndPosition);
    }

    private boolean checkLineByType(ChessPieceType type, int xStartPosition,
                                    int yStartPosition, int xEndPosition, int yEndPosition) {
        switch (type) {
            case ROOK:
                if (xStartPosition == xEndPosition) {
                    return checkVertically(xStartPosition, yStartPosition, yEndPosition);
                } else {
                    return checkHorizontally(xStartPosition, yStartPosition, xEndPosition);
                }
            case RUNNER:
                return checkDiagonal(xStartPosition, yStartPosition, xEndPosition, yEndPosition);
            case QUEEN:
                System.out.println("QUEEN");
                break;
        }
        return true;
    }

    private boolean checkVertically(int xStartPosition, int yStartPosition, int yEndPosition) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getX() == xStartPosition) {
                if (yStartPosition < yEndPosition) {
                    if (chessPiece.getY() > yStartPosition && chessPiece.getY() < yEndPosition) {
                        return false;
                    }
                } else {
                    if (chessPiece.getY() < yStartPosition && chessPiece.getY() > yEndPosition) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkHorizontally(int xStartPosition, int yStartPosition, int xEndPosition) {
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece.getY() == yStartPosition) {
                if (xStartPosition < xEndPosition) {
                    if (chessPiece.getX() > xStartPosition && chessPiece.getX() < xEndPosition) {
                        return false;
                    }
                } else {
                    if (chessPiece.getX() < xStartPosition && chessPiece.getX() > xEndPosition) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkDiagonal(int xStartPosition, int yStartPosition, int xEndPosition, int yEndPosition) {
        for (int x = (xStartPosition + 1), y = (yStartPosition + 1); x < xEndPosition && y < yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        for (int x = (xStartPosition + 1), y = (yStartPosition - 1); x < xEndPosition && y > yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        for (int x = (xStartPosition - 1), y = (yStartPosition + 1); x > xEndPosition && y < yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        for (int x = (xStartPosition - 1), y = (yStartPosition - 1); x > xEndPosition && y > yEndPosition; x++, y++) {
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece.getX() == x && chessPiece.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }
}
