
package com.conway.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.graphics.Color;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;
import android.os.SystemClock;

import com.conway.gameoflife.backend.GameEngine;
import com.conway.gameoflife.frontend.GameBoardView;

public class MainActivity extends Activity {

    private GameEngine game;
    private GameBoardView gameBoardView;

    private TextView generationText;
    private TextView aliveText;
    private TextView statusText;

    private Button startButton;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private boolean running = false;
    private long updateDelay = 550;
    private static final int REQUIRED_TABS=7;
    private static final long TAP_TIME_LIMIT=2500;

    private int generationTapCount=0;
    private long firstGenerationTapTime=0;

    private final Runnable gameLoop = new Runnable() {
        @Override
        public void run() {
            if (running) {
                game.nextGeneration();
                updateInformation();
                gameBoardView.refreshBoard();

                handler.postDelayed(this, updateDelay);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new GameEngine(30, 20);

        createInterface();
        updateInformation();
    }

    private void createInterface() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(16), dp(20), dp(16), dp(12));
        root.setBackgroundColor(Color.rgb(247, 249, 248));
        TextView title = new TextView(this);
        title.setText("Игра жизни");
        title.setTextSize(27);
        title.setTextColor(Color.rgb(31, 41, 35));
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Клеточный автомат Конвея");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.rgb(120, 130, 123));

        LinearLayout.LayoutParams subtitleParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        subtitleParams.bottomMargin = dp(18);
        root.addView(subtitle, subtitleParams);

        // Информационные карточки

        LinearLayout infoRow = new LinearLayout(this);
        infoRow.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout generationCard = createInfoCard(
                "ПОКОЛЕНИЕ",
                "0"
        );

        LinearLayout aliveCard = createInfoCard(
                "ЖИВЫЕ КЛЕТКИ",
                "0"
        );

        generationText = (TextView) generationCard.getChildAt(1);
        aliveText = (TextView) aliveCard.getChildAt(1);
        generationCard.setOnClickListener(v->handlerGenerationTapToCard());

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(76),
                        1
                );

        cardParams.rightMargin = dp(8);

        infoRow.addView(generationCard, cardParams);

        LinearLayout.LayoutParams secondCardParams =
                new LinearLayout.LayoutParams(
                        0,
                        dp(76),
                        1
                );

        secondCardParams.leftMargin = dp(8);

        infoRow.addView(aliveCard, secondCardParams);

        LinearLayout.LayoutParams infoParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        infoParams.bottomMargin = dp(16);

        root.addView(infoRow, infoParams);

        // Игровое поле

        FrameLayout boardContainer = new FrameLayout(this);
        boardContainer.setBackgroundResource(R.drawable.board_background);
        boardContainer.setPadding(dp(6), dp(6), dp(6), dp(6));

        gameBoardView = new GameBoardView(this, game);

        boardContainer.addView(
                gameBoardView,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )
        );

        LinearLayout.LayoutParams boardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        boardParams.bottomMargin = dp(12);

        root.addView(boardContainer, boardParams);

        statusText = new TextView(this);
        statusText.setText("Игра остановлена");
        statusText.setTextSize(13);
        statusText.setTextColor(Color.rgb(110, 120, 113));
        statusText.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams statusParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(30)
                );

        root.addView(statusText, statusParams);

        // Кнопки управления

        LinearLayout firstRow = createButtonRow();

        startButton = createButton(
                "Старт",
                R.drawable.button_primary,
                Color.WHITE
        );

        Button stepButton = createButton(
                "Шаг",
                R.drawable.button_background,
                Color.rgb(40, 90, 60)
        );

        firstRow.addView(
                startButton,
                createButtonParams(1)
        );

        firstRow.addView(
                stepButton,
                createButtonParams(1)
        );

        LinearLayout.LayoutParams firstRowParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(48)
                );

        firstRowParams.bottomMargin = dp(8);

        root.addView(firstRow, firstRowParams);

        // Дополнительные кнопки

        LinearLayout secondRow = createButtonRow();

        Button randomButton = createButton(
                "Случайно",
                R.drawable.button_background,
                Color.rgb(40, 90, 60)
        );

        Button clearButton = createButton(
                "Очистить",
                R.drawable.button_background,
                Color.rgb(40, 90, 60)
        );

        secondRow.addView(
                randomButton,
                createButtonParams(1)
        );

        secondRow.addView(
                clearButton,
                createButtonParams(1)
        );

        LinearLayout.LayoutParams secondRowParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(48)
                );

        secondRowParams.bottomMargin = dp(10);

        root.addView(secondRow, secondRowParams);

        // Скорость игры

        TextView speedLabel = new TextView(this);
        speedLabel.setText("Скорость симуляции");
        speedLabel.setTextSize(13);
        speedLabel.setTextColor(Color.rgb(90, 100, 93));

        root.addView(speedLabel);

        SeekBar speedBar = new SeekBar(this);
        speedBar.setMax(19);
        speedBar.setProgress(10);

        speedBar.setProgressTintList(
                ColorStateList.valueOf(Color.rgb(34, 160, 107))
        );

        speedBar.setThumbTintList(
                ColorStateList.valueOf(Color.rgb(34, 160, 107))
        );

        speedBar.setProgressBackgroundTintList(
                ColorStateList.valueOf(Color.rgb(220, 228, 222))
        );

        root.addView(
                speedBar,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(38)
                )
        );

        setContentView(root);

        // Обработчики кнопок

        startButton.setOnClickListener(v -> toggleGame());

        stepButton.setOnClickListener(v -> {
            if (!running) {
                game.nextGeneration();
                updateInformation();
                gameBoardView.refreshBoard();
            }
        });

        randomButton.setOnClickListener(v -> {
            pauseGame();

            game.randomizeBoard(0.3);

            updateInformation();
            gameBoardView.refreshBoard();
        });

        clearButton.setOnClickListener(v -> {
            pauseGame();

            game.clearBoard();

            updateInformation();
            gameBoardView.refreshBoard();
        });

        speedBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser
                    ) {
                        updateDelay = 1000 - progress * 45;

                        if (running) {
                            restartGameLoop();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
    }

    private LinearLayout createInfoCard(String label, String value) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(14), dp(8), dp(8), dp(8));
        card.setBackgroundResource(R.drawable.board_background);

        TextView labelText = new TextView(this);
        labelText.setText(label);
        labelText.setTextSize(10);
        labelText.setTextColor(Color.rgb(120, 130, 123));

        TextView valueText = new TextView(this);
        valueText.setText(value);
        valueText.setTextSize(21);
        valueText.setTextColor(Color.rgb(34, 160, 107));
        valueText.setTypeface(null, android.graphics.Typeface.BOLD);

        card.addView(labelText);
        card.addView(valueText);

        return card;
    }

    private LinearLayout createButtonRow() {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        return row;
    }

    private Button createButton(
            String text,
            int background,
            int textColor
    ) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextSize(14);
        button.setTextColor(textColor);
        button.setAllCaps(false);
        button.setTypeface(null, android.graphics.Typeface.BOLD);
        button.setBackgroundResource(background);
        button.setPadding(dp(8), 0, dp(8), 0);

        return button;
    }

    private LinearLayout.LayoutParams createButtonParams(int weight) {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        weight
                );

        params.leftMargin = dp(4);
        params.rightMargin = dp(4);

        return params;
    }

    private void toggleGame() {

        if (running) {
            pauseGame();
        } else {
            startGame();
        }
    }

    private void startGame() {

        if (running) {
            return;
        }

        running = true;

        startButton.setText("Пауза");
        startButton.setBackgroundResource(R.drawable.button_background);
        startButton.setTextColor(Color.rgb(40, 90, 60));

        statusText.setText("Симуляция запущена");

        handler.removeCallbacks(gameLoop);
        handler.postDelayed(gameLoop, updateDelay);
    }

    private void pauseGame() {

        running = false;

        handler.removeCallbacks(gameLoop);

        if (startButton != null) {
            startButton.setText("Старт");
            startButton.setBackgroundResource(R.drawable.button_primary);
            startButton.setTextColor(Color.WHITE);
        }

        if (statusText != null) {
            statusText.setText("Игра остановлена");
        }
    }

    private void restartGameLoop() {

        handler.removeCallbacks(gameLoop);

        if (running) {
            handler.postDelayed(gameLoop, updateDelay);
        }
    }

    private void updateInformation() {

        generationText.setText(
                String.valueOf(game.getGeneration())
        );

        aliveText.setText(
                String.valueOf(game.getBoard().countAliveCells())
        );
    }

    private int dp(int value) {

        return (int) (
                value * getResources().getDisplayMetrics().density
        );
    }

    @Override
    protected void onDestroy() {

        pauseGame();

        super.onDestroy();
    }
    private void handlerGenerationTapToCard(){
        long currentTime=SystemClock.elapsedRealtime();
        if(generationTapCount==0 || currentTime-firstGenerationTapTime>TAP_TIME_LIMIT){
            generationTapCount=1;
            firstGenerationTapTime=currentTime;

        }else{
            generationTapCount++;
        }
        if(generationTapCount>=REQUIRED_TABS){
            generationTapCount=0;
            firstGenerationTapTime=0;
            Intent intent=new Intent(MainActivity.this,AuthorActivity.class);
            startActivity(intent);
        }
    }
}
