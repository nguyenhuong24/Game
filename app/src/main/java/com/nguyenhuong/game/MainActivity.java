package com.nguyenhuong.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long TIME_FALL = 2000;
    private ImageView imgStart, imgBall, imgSquare;
    private TextView tvScore;
    private Button btnLeft, btnRight;
    private int mDegree = 0;
    private int mBallColor = 0;
    private Random mRandom;
    private int mSquareColor;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        imgBall = findViewById(R.id.img_ball);
        imgSquare = findViewById(R.id.img_square);
        imgStart = findViewById(R.id.img_start);
        tvScore = findViewById(R.id.tv_score);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        imgStart.setOnClickListener(this);
        mRandom = new Random();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                rotateLeft();
                break;
            case R.id.btn_right:
                rotateRight();
                break;
            case R.id.img_start:
                startGame();
                break;
        }
    }

    private void startGame() {
        imgStart.setVisibility(View.GONE);
        mDegree = 0;
        mBallColor = 0;
        mSquareColor = 0;
        mScore = 0;
        fallBall();
    }

    private void fallBall() {
        mBallColor = mRandom.nextInt(4);
        imgBall.setImageResource(R.drawable.ball_0 + mBallColor);
        TranslateAnimation fallAnimation =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_PARENT, 0f,
                        Animation.RELATIVE_TO_PARENT, 0.85f);
        fallAnimation.setDuration(TIME_FALL);
        imgBall.startAnimation(fallAnimation);
        fallAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("doanpt", " SquareColor=" + mSquareColor + " -- BallColor=" + mBallColor);
                if (mSquareColor == mBallColor) {
                    mScore++;
                    tvScore.setText(String.valueOf(mScore));
                    fallBall();
                } else {
                    tvScore.setText("GameOver");
                    imgStart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void rotateRight() {
        RotateAnimation leftAnimation = new
                RotateAnimation(mDegree, mDegree + 90
                , Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f);
        leftAnimation.setDuration(200);
        leftAnimation.setFillAfter(true);
        mDegree += 90;
        mSquareColor--;
        if (mSquareColor < 0) {
            mSquareColor = 3;
        }
        imgSquare.startAnimation(leftAnimation);
    }

    private void rotateLeft() {
        RotateAnimation leftAnimation = new
                RotateAnimation(mDegree, mDegree - 90
                , Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f);
        leftAnimation.setDuration(200);
        leftAnimation.setFillAfter(true);
        mDegree -= 90;
        mSquareColor++;
        if (mSquareColor > 3) {
            mSquareColor = 0;
        }
        imgSquare.startAnimation(leftAnimation);
    }
}
