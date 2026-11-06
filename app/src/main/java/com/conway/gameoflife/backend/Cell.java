package com.conway.gameoflife.backend;
public class Cell {
    private boolean alive;
    public Cell() {
        this.alive = false;
    }
    public Cell(boolean alive) {
        this.alive = alive;
    }
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void toggle() {
        this.alive = !this.alive;
    }
}