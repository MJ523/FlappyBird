package com.example.flapybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.lang.Math;

import static com.example.flapybird.GameView.screenRatioX;
import static com.example.flapybird.GameView.screenRatioY;

public class Column {
    int x, y, width, height, yVelocity;
    Bitmap column;
    boolean flap = false;

    public Column(int screenX, int screenY, Resources res) {
        column = BitmapFactory.decodeResource(res, R.drawable.column2);

        width = column.getWidth();
        height = column.getHeight();

        width *= 3.5;
        height *= 3.5;


        column = Bitmap.createScaledBitmap(column, width, height, false);

        y = (int) (-700 * Math.random());; // -20 to -700;
        x = (screenX);

    }

    Bitmap getColumn() {
        return column;
    }
}
