package com.example.flapybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.flapybird.GameView.screenRatioX;
import static com.example.flapybird.GameView.screenRatioY;

public class Bird {
    int x, y, width, height, yVelocity;
    Bitmap bird;
    boolean flap = false;

    public Bird(int screenX, int screenY, Resources res) {
        bird = BitmapFactory.decodeResource(res, R.drawable.bird);

        width = bird.getWidth();
        height = bird.getHeight();

        width /= 15;
        height /= 15;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        bird = Bitmap.createScaledBitmap(bird, width, height, false);

        y = (screenY - height) / 2;
        x = (screenX - width) / 2;

    }

    Bitmap getBird() {
        return bird;
    }
}
