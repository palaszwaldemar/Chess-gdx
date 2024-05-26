package com.mygdx.chess.server;

import java.util.Objects;

import static com.mygdx.chess.server.ChessPieceColor.BLACK;
import static com.mygdx.chess.server.ChessPieceColor.WHITE;

public abstract class ChessPiece implements Cloneable {
    int x;
    int y;
    private final ChessPieceColor color;
    private final ChessPieceType type;
    boolean moved;

    ChessPiece(ChessPieceDto chessPieceDto) {
        x = chessPieceDto.x();
        y = chessPieceDto.y();
        color = chessPieceDto.color();
        type = chessPieceDto.type();
    }

    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChessPieceColor getColor() {
        return color;
    }

    public ChessPieceType getType() {
        return type;
    }

    public boolean wasNotMoved() {
        return !moved;
    }

    public abstract boolean isCorrectMovement(int x, int y);

    public boolean hasSameColor(ChessPiece chessPiece) {
        return color == chessPiece.getColor();
    }

    public ChessPieceColor getEnemyColor() {
        ChessPieceColor enemyColor;
        if (hasColor(WHITE)) {
            enemyColor = BLACK;
        } else {
            enemyColor = WHITE;
        }
        return enemyColor;
    }

    public boolean hasColor(ChessPieceColor color) {
        return this.color == color;
    }

    public boolean hasType(ChessPieceType type) {
        return this.type == type;
    }

    void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return getX() == that.getX() && getY() == that.getY() && moved == that.moved && getColor() == that.getColor() &&
            getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getColor(), getType(), moved);
    }

    @Override
    public ChessPiece clone() {
        try {
            return (ChessPiece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
