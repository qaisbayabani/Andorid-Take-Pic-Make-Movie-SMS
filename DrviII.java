package com.example.bongi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by khan on 5/13/2016.
 */
class DrviII extends View {

    Context context;
    private Paint mPaint;

    private float mX1=300, mY1=300;

    private String line,line1;
    private String ss2;

    private static final float TOLERANCE = 5;
    String s1;

    public DrviII (Context c, String ss1) {
        super(c);

        context = c;


        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ss2=ss1;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        //mPaint.setStrokeJoin(Paint.Join.ROUND);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = 2000;
        int height = 7000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);


        File f1 =
        new File("/sdcard/smslo/"+ss2);

        try {
            BufferedReader br = new BufferedReader(new FileReader(f1));
            while ((line = br.readLine()) != null) {

                //drawTextAndBreakLine(canvas, mPaint,mX1, mY1, 20, line);
               drawMultilineText(line, (int)mX1,(int) mY1, mPaint,canvas, 20, new Rect(0,0,700,7000));
                //canvas.drawText(line, mX1, mY1, mPaint);
                mY1+=50;
            }
            br.close();


        } catch (IOException e) {
        }

    }

    void drawMultilineText(String str, int x, int y, Paint paint, Canvas canvas, int fontSize, Rect drawSpace) {
        int      lineHeight = 0;
        int      yoffset    = 0;
        String[] lines      = str.split(" ");

        // set height of each line (height of text + 20%)
        lineHeight = (int) (calculateHeightFromFontSize(str, fontSize) * 2.5);
        // draw each line
        String line = "";
        for (int i = 0; i < lines.length; ++i) {

            if(calculateWidthFromFontSize(line + " " + lines[i], fontSize) <= drawSpace.width()){
                line = line + " " + lines[i];

            }else{
                canvas.drawText(line, x, y + yoffset, paint);
                yoffset = yoffset + (lineHeight);
                line = lines[i];
                mY1+=25;
            }



        }
        canvas.drawText(line, x, y + yoffset, paint);


    }

    private int calculateWidthFromFontSize(String testString, int currentSize)
    {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(currentSize);
        paint.getTextBounds(testString, 0, testString.length(), bounds);

        return (int) Math.ceil( bounds.width());
    }

    private int calculateHeightFromFontSize(String testString, int currentSize)
    {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(currentSize);
        paint.getTextBounds(testString, 0, testString.length(), bounds);

        return (int) Math.ceil( bounds.height());
    }


}