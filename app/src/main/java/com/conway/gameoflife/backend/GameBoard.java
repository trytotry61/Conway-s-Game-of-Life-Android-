package com.conway.gameoflife.backend;

import java.util.Random;

public class GameBoard {

    private final int width;
    private final int height;
    private final Cell[][] cells;

    public GameBoard(int width, int height) {

        if (width < 3 || height < 3) {
            throw new IllegalArgumentException(
                    "Размер поля должен быть не меньше 3x3"
            );
        }

        this.width = width;
        this.height = height;

        cells = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive(int row, int col) {
        return cells[row][col].isAlive();
    }

    public void setCell(int row, int col, boolean alive) {
        cells[row][col].setAlive(alive);
    }

    public void toggleCell(int row, int col) {
        cells[row][col].toggle();
    }

    public void clear() {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col].setAlive(false);
            }
        }
    }
    public void randomize(double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException(
                    "Вероятность должна находиться от 0 до 1"
            );
        }
        Random random = new Random();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                boolean alive = random.nextDouble() < probability;
                cells[row][col].setAlive(alive);
            }
        }
    }
    public int countAliveCells() {
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (cells[row][col].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }
}