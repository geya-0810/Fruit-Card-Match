package com.example.fruitcardmatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;


public class Game extends AppCompatActivity implements View.OnClickListener {
    TextView tC, cC;
    private final int[] imageViewCardIds = {R.id.iv_11, R.id.iv_12, R.id.iv_13, R.id.iv_14, R.id.iv_15, R.id.iv_16, R.id.iv_21, R.id.iv_22, R.id.iv_23, R.id.iv_24, R.id.iv_25, R.id.iv_26, R.id.iv_31, R.id.iv_32, R.id.iv_33,
                                            R.id.iv_34, R.id.iv_35, R.id.iv_36, R.id.iv_41, R.id.iv_42, R.id.iv_43, R.id.iv_44, R.id.iv_45, R.id.iv_46, R.id.iv_51, R.id.iv_52, R.id.iv_53, R.id.iv_54, R.id.iv_55, R.id.iv_56};
    private final int[] drawableCardIds = {R.drawable.f_apple, R.drawable.f_avocado, R.drawable.f_banana, R.drawable.f_blueberry, R.drawable.f_cherry, R.drawable.f_grape, R.drawable.f_kiwi,
            R.drawable.f_lemon, R.drawable.f_mango, R.drawable.f_orange, R.drawable.f_pear,  R.drawable.f_pineapple, R.drawable.f_raspberry, R.drawable.f_strawberry, R.drawable.overtime07};
    private CountDownTimer timer;
    private long timeRemaining = 121000; // set 120s as default
    int matchCount = 0, totalMatch = 0, totalFlip = 0, round = 0;
    boolean isGameOver = false; // define the game is over or continue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        Log.d("GameDebug11", "Activity Game layout loaded successfully.");

        MediaPlayer mediaPlayer = Music.getMediaPlayer(this);

        cC = findViewById(R.id.cardCounter);
        tC = findViewById(R.id.timeCounter);
        startTimer();
        setupNewGame();
    }

    private void setupNewGame(){
        ArrayList<Integer> shuffledImages = new ArrayList<>();
        for (int id: drawableCardIds) {
            shuffledImages.add(id);
            shuffledImages.add(id);
        }
        Collections.shuffle(shuffledImages);
        Collections.shuffle(shuffledImages);

        for (int i = 0; i < shuffledImages.size(); i++) {
            int imageViewId = imageViewCardIds[i];
            int cardId = shuffledImages.get(i);
            Card card = new Card(imageViewId, cardId);
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(R.drawable.card_back);
            if (imageView == null) {
                Log.e("GameDebug12", "ImageView with ID " + imageViewId + " is null.");
            }
            imageView.setTag(card);
            imageView.setOnClickListener(this);
        }
//        Log.d("GameDebug1", "setupNewGame function started.");
        round++;
        firstCard = null;
        cC.setText("Match Count: \n" + matchCount + "/30");
    }

    Card firstCard = null, secondCard = null;

    @Override
    public void onClick(View v) {
        ImageView imageView = (ImageView) v;
        Card card = (Card) imageView.getTag();
//      Log.d("demo", "onClick: " + card);

        if(!card.isFlipped() && !card.isMatched()){
            imageView.setImageResource(card.getCardId());
            card.setFlipped(true);

            if(firstCard == null){
                firstCard = card;
                totalFlip += 1;
            } else {
                secondCard = card;
                totalFlip += 1;
                setAllCardsEnabled(false);

                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //check if the selected image are equal
                        checkMatch();
                    }
                }, 500);
//                    Log.d("GameDebug2", "matchCount = " + matchCount + ", isGameOver = " + isGameOver);
            }
        }
    }

    private void checkMatch() {
        if(firstCard.getCardId() == secondCard.getCardId()){    //if match
            matchCount += 2;
            totalMatch += 2;
            // Set the matched cards to be invisible
            firstCard.setMatched(true);
            ImageView firstImageView = findViewById(firstCard.getImageViewId());
            firstImageView.setVisibility(View.INVISIBLE);

            secondCard.setMatched(true);
            ImageView secondImageView = findViewById(secondCard.getImageViewId());
            secondImageView.setVisibility(View.INVISIBLE);

            if (firstCard.getCardId() == R.drawable.overtime07) {   // check it the matched card are add time card
                timeRemaining += 20000;  // add 15sec
                timer.cancel(); // stop the time counter for add time
                startTimer();   // restart the time counter
            }
//            Log.d("GameDebug4", "matchCount = " + matchCount + ", isGameOver = " + isGameOver);
            cC.setText("Match Count: \n" + matchCount + "/30");

            if (matchCount == 30 && !isGameOver) {
                timer.cancel(); // stop timer when restarting game
                cC.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d("GameDebug5", "checkMatch is called.");
                        for (int id : imageViewCardIds) {
                            ImageView imageView = findViewById(id);
                            imageView.setVisibility(View.VISIBLE);
                        }
                        matchCount = 0;
                        setupNewGame();
                        startTimer();
                    }
                }, 500);
            }
        } else {//not match
            coverCard(firstCard);
            coverCard(secondCard);
        }
        firstCard = null;
        setAllCardsEnabled(true);
    }

    private void startTimer() { // set the timer counter
        timer = new CountDownTimer(timeRemaining, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                tC.setText(String.format("Time Remaining: \n%d s", timeRemaining / 1000));
//                long minutes = (millisUntilFinished / 1000) / 60;
//                long seconds = (millisUntilFinished / 1000) % 60 - 1;
//
//                String timeFormatted = String.format("%02d:%02d", minutes, seconds);
//                tC.setText("Time Remaining: \n" + timeFormatted);
            }

            public void onFinish() {
                gameEnd();
            }
        }.start();
    }

    private void setAllCardsEnabled(boolean enabled) {  // set all card can click or not
        for (int id : imageViewCardIds) {
            ImageView imageView = findViewById(id);
            imageView.setEnabled(enabled);
        }
    }

    private void gameEnd() {    // set the game over
        isGameOver = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder
                .setMessage("Game Over!\nRound: " + round + "\nTotal Eliminated: " + totalMatch +
                        "\nTotal card flipping times: " + totalFlip)
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent = new Intent(getApplicationContext(), Game.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                });

        if (!isFinishing() && !isDestroyed()) {  // make sure activity is still valid
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void coverCard(Card card){
        ImageView imageView = findViewById(card.getImageViewId());
        imageView.setImageResource(R.drawable.card_back);
        card.setFlipped(false);
        card.setMatched(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();  // when activity is destroyed, cancel the timer to prevent crashes
        }
    }
}
