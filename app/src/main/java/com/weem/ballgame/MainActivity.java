package com.weem.ballgame;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.weem.ballgame.Service.MusicService;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Ball.BallListener {

    public static final String TAG = "meroball";
    public static boolean isGameOver;
    ViewGroup mContentView;
    ImageView music;
    int mScreenWidth = 500, mScreenHeight;
    boolean isMusicPlaying = true;
    int images[] = {R.drawable.foot, R.drawable.basket, R.drawable.volley};
    int ballImage;
    int totalScore;
    TextView totalScoreTV;
    int life = 4;
    ImageView life1, life2, life3, life4;
    int noofball;
    Ballthrower ballthrower;
    boolean isGameCancelled;
    ImageView playButton;
    private int ballCount;
    private long levelSleepTime = 1500;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContentView = findViewById(R.id.activity_main);
        setFullScreen();


        final ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            mScreenWidth = mContentView.getWidth();
                            Log.i(TAG, "onGlobalLayout: " + mScreenWidth);
                            mScreenHeight = mContentView.getHeight();
                        }
                    }
            );


        }


        music = findViewById(R.id.music);
        playButton = findViewById(R.id.playButton);
        totalScoreTV = findViewById(R.id.totalScore);
        final Intent musicIntent = new Intent(getApplicationContext(), MusicService.class);
        music.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isMusicPlaying) {

                            stopService(musicIntent);
                            isMusicPlaying = false;
                            music.setImageResource(R.drawable.ic_music_off);

                        } else {
                            startService(musicIntent);

                            isMusicPlaying = true;
                            music.setImageResource(R.drawable.ic_music);


                        }

                        Log.i("servicesee", "onClick: " + isMusicPlaying);
                    }
                }
        );
        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);
        life4 = findViewById(R.id.life4);


        playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        isGameOver = false;


                        setVariablesToZero();


                        Ballthrower ballthrower = new Ballthrower();
                        ballthrower.execute();
                        playButton.setVisibility(View.INVISIBLE);

                    }
                }
        );


    }

    private void setVariablesToZero() {


        totalScore = 0;

        life = 4;

        noofball = 0;

        isGameCancelled = false;
        ballCount = 0;
        levelSleepTime = 1500;
        level = 0;
        life1.setImageResource(R.drawable.ic_accessibility_black_24dp);
        life2.setImageResource(R.drawable.ic_accessibility_black_24dp);
        life3.setImageResource(R.drawable.ic_accessibility_black_24dp);
        life4.setImageResource(R.drawable.ic_accessibility_black_24dp);
        totalScoreTV.setText("Score: 0");


    }

    private void manageLevelSleepTime() {

        if (noofball > 25) {

            levelSleepTime = 200;

        } else if (noofball > 70) {

            levelSleepTime = 180;


        } else if (noofball > 85) {

            levelSleepTime = 150;


        } else if (noofball > 105) {

            levelSleepTime = 100;


        } else if (noofball > 130) {

            levelSleepTime = 90;


        } else if (noofball > 140) {

            levelSleepTime = 80;


        } else if (noofball > 200) {

            levelSleepTime = 60;


        } else if (noofball > 250) {

            levelSleepTime = 50;


        } else if (noofball > 300) {

            levelSleepTime = 40;


        }
    }

    private void createRandomBall() {


        Ball ball = new Ball(getApplicationContext());

        Random random = new Random();

        Log.i(TAG, "createRandomBall: " + mScreenWidth);


        int xBallLocation = random.nextInt(mScreenWidth - 200);


        ball.createBall(MainActivity.this, 70);
        ball.setX(xBallLocation);
        ball.setY(mScreenHeight);
        ball.setImageResource(getBallImage());
        ballImage++;
        mContentView.addView(ball);
        ball.animateBall(mScreenHeight);


    }

    private int getBallImage() {
        if (ballImage >= images.length) {
            ballImage = 0;
        }
        return images[ballImage];
    }

    private void setFullScreen() {

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


    }

    @Override
    protected void onResume() {
        super.onResume();

        setFullScreen();
        startService(new Intent(getApplicationContext(), MusicService.class));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplicationContext(), MusicService.class));

        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(getApplicationContext(), MusicService.class));

    }

    @Override
    public void popBall(final Ball ball, boolean userTouch, Context context) {

        mContentView.removeView(ball);

        if (userTouch) {
            totalScore++;

            totalScoreTV.setText("Score : " + totalScore);


        } else {

            life--;
            switch (life) {

                case (1):
                    life1.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                    life2.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                    life3.setImageResource(R.drawable.ic_delete_forever_black_24dp);


                    break;

                case (2):
                    life1.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                    life2.setImageResource(R.drawable.ic_delete_forever_black_24dp);

                    break;

                case (3):
                    life1.setImageResource(R.drawable.ic_delete_forever_black_24dp);

                    break;

                case (0):

                    life1.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                    life2.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                    life3.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                    life4.setImageResource(R.drawable.ic_delete_forever_black_24dp);


                    isGameOver = true;
                    playButton.setVisibility(View.VISIBLE);
                    isGameCancelled = true;


                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO: 11/13/17 start game

        super.onBackPressed();


    }

    public class Ballthrower extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... integers) {

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            while (!isGameCancelled) {


                while (ballCount < 5) {
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {


                            createRandomBall();

                            ballCount++;
                            noofball++;

                        }


                    });
                    try {


                        Thread.sleep(levelSleepTime);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }

                }

                level++;


                levelSleepTime = Math.max(300, levelSleepTime - level * 500);

                manageLevelSleepTime();

                Log.i("timeandtide", "doInBackground:level sleep time " + levelSleepTime);
                ballCount = 0;

            }
            return null;
        }
    }
}
