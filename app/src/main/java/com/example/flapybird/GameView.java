package com.example.flapybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.Math;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    public static float screenRatioX;
    public static float screenRatioY;
    private Bird bird;
    private Column column;
    private Paint paint;
    private int screenX;
    private int screenY;
    private int score = 0;
    private boolean added = false;
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1080 / screenX;
        screenRatioY = 1920 / screenY;
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.x = screenX;
        paint = new Paint();
        bird = new Bird(screenX, screenY, getResources());
        column = new Column(screenX, screenY, getResources());
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    private void update() {
        background1.x -= 5 * screenRatioX;
        background2.x -= 5 * screenRatioX;
        if (background1.x < -1 * background1.background.getWidth()) {
            background1.x = screenX;
        }
        if (background2.x < -1 * background2.background.getWidth()) {
            background2.x = screenX;
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(column.getColumn(), column.x, column.y, paint);
            canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            paint.setTextSize(100);
            canvas.drawText(score + "", screenX / 2, 100, paint);

            getHolder().unlockCanvasAndPost(canvas);
            if (bird.flap) {
                bird.yVelocity = (int) (-15 * screenRatioY);
                bird.flap = false;
            } else {
                bird.yVelocity += (int) (1.5 * screenRatioY);
                if (bird.yVelocity >= 1000) {
                    bird.yVelocity = 1000;
                }

            }

            bird.y += bird.yVelocity;

            if (bird.y < 0) {
                bird.y = 0;
            }
            if (bird.y > screenY - bird.height) {
                bird.y = screenY - bird.height;
            }

            column.x -= 10;
            if (column.x < 0 - column.width) {
                column.x = screenX;
                column.y = (int) (-700 * Math.random());
                added = false;
            }
            if (Math.abs((column.x + column.width / 2) - (screenX / 2 )) <= 150) {
                if (added == false) {
                    score++;
                    added = true;
                }
                if (bird.y > column.y + 1250 || bird.y < column.y + 1000) {
                    pause();
                }

            }


        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.flap = true;
        }

        return true;
    }
}
