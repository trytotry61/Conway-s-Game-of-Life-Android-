package com.conway.gameoflife.backend;
public class GameRules {
    public int countAliveNeighbors(
            GameBoard board,
            int row,
            int col
    ) {
        int count = 0;
        int height = board.getHeight();
        int width = board.getWidth();
        for (int dr = -1; dr <= 1; dr++) {

            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                int neighborRow = (row + dr + height) % height;
                int neighborCol = (col + dc + width) % width;

                if (board.isAlive(neighborRow, neighborCol)) {
                    count++;
                }
            }
        }

        return count;
    }
    public boolean calculateNextState(
            boolean alive,
            int neighbors
    ) {
        if (alive && (neighbors == 2 || neighbors == 3)) {
            return true;
        }
        if (!alive && neighbors == 3) {
            return true;
        }
        return false;
    }
}