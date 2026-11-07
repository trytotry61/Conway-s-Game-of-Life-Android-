
package com.conway.gameoflife.frontend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.conway.gameoflife.backend.GameEngine;
import com.conway.gameoflife.backend.GameBoard;

public class GameBoardView extends View {

    private final GameEngine game;


    private final Paint cellPaint;
    private final Paint gridPaint;
    private final ScaleZoom zoom;
    private float cellSize;
    private float offsetX;
    private float offsetY;

    public GameBoardView(Context context, GameEngine game) {

        super(context);

        this.game = game;
        zoom=new ScaleZoom(context);
        cellPaint = new Paint();
        cellPaint.setColor(Color.rgb(34, 160, 107));
        cellPaint.setStyle(Paint.Style.FILL);
        cellPaint.setAntiAlias(true);

        gridPaint = new Paint();
        gridPaint.setColor(Color.rgb(229, 235, 230));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        GameBoard board = game.getBoard();

        int width = board.getWidth();
        int height = board.getHeight();

        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        cellSize = Math.min(
                getWidth() / (float) width,
                getHeight() / (float) height
        );

        float boardWidth = cellSize * width;
        float boardHeight = cellSize * height;

        offsetX = (getWidth() - boardWidth) / 2;
        offsetY = (getHeight() - boardHeight) / 2;
        canvas.save();
        canvas.concat(zoom.getMatrix());

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {

                float left = offsetX + col * cellSize;
                float top = offsetY + row * cellSize;

                float right = left + cellSize;
                float bottom = top + cellSize;

                if (board.isAlive(row, col)) {

                    canvas.drawRect(
                            left + 1,
                            top + 1,
                            right - 1,
                            bottom - 1,
                            cellPaint
                    );
                }

                canvas.drawRect(
                        left,
                        top,
                        right,
                        bottom,
                        gridPaint
                );
            }
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getActionMasked()==MotionEvent.ACTION_UP && !zoom.hasMoved()) {

            float[] point=zoom.mapTouchToBoard(event.getX(),event.getY());

            int col = (int) ((point[0] - offsetX) / cellSize);
            int row = (int) ((point[1] - offsetY) / cellSize);

            GameBoard board = game.getBoard();

            if (row >= 0 && row < board.getHeight()
                    && col >= 0 && col < board.getWidth()) {

                board.toggleCell(row, col);

                invalidate();
            }

            return true;
        }

        return true;
    }

    public void refreshBoard() {

        invalidate();
    }
}
