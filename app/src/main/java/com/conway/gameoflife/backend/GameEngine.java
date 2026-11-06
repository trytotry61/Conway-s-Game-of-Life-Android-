package com.conway.gameoflife.backend;

public class GameEngine {

    private GameBoard board;
    private final GameRules rules;

    private int generation;

    public GameEngine(int width, int height) {

        board = new GameBoard(width, height);
        rules = new GameRules();

        generation = 0;
    }
    public GameBoard getBoard() {
        return board;
    }
    public int getGeneration() {
        return generation;
    }
    public int getAliveCellsCount() {
        return board.countAliveCells();
    }
    public void nextGeneration() {
        int height = board.getHeight();
        int width = board.getWidth();
        boolean[][] nextGeneration = new boolean[height][width];
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {

                int neighbors = rules.countAliveNeighbors(
                        board, row, col
                );

                boolean currentState = board.isAlive(row, col);

                nextGeneration[row][col] =
                        rules.calculateNextState(
                                currentState, neighbors
                        );
            }
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                board.setCell(
                        row,
                        col,
                        nextGeneration[row][col]
                );
            }
        }
        generation++;
    }
    public void clearBoard() {

        board.clear();
        generation = 0;
    }
    public void randomizeBoard(double probability) {

        board.randomize(probability);
        generation = 0;
    }
    public void resetBoard(int width, int height) {

        board = new GameBoard(width, height);
        generation = 0;
    }
}