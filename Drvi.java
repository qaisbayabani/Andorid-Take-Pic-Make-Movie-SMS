package com.example.bongi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;

public class Drvi extends View {

    Context context;

    private Paint mPaint;
    private int mX1=10, mY1=300;
    Rect rec[]= new Rect[100];
    Rect rec1[]= new Rect[100];
    private static final float TOLERANCE = 5;
    String s1;
    int sp;

    File f =new File("/sdcard/smslo/");

    File[] file = f.listFiles();

    public Drvi (Context c) {
        super(c);

        context = c;

        // we set a new Path

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();

        mPaint.setColor(Color.RED);

        mPaint.setTextSize(50);
       // mPaint.setStrokeWidth(10);
        //mPaint.setStrokeJoin(Paint.Join.ROUND);


    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Compute the height required to render the view
        // Assume Width will always be MATCH_PARENT.
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 8000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }



    @Override
    protected void onDraw(Canvas canvas) {





        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw




        //Log.d("Files", "Size: "+ file.length);



        for (int i=0; i < file.length; i++)
        {

            s1 =file[i].getName();


            canvas.drawText(s1 , mX1 , mY1, mPaint);

            rec[i]=new Rect(mX1-100 , mY1-100 , mX1+400 , mY1+100);

            canvas.drawText("Delete",mX1+400,mY1,mPaint);
            rec1[i]=new Rect(mX1+400 , mY1-100 , mX1+600 , mY1+100);


            //  canvas.drawRect(rec[i],mPaint);

            mY1+=150;
            sp=i;
              Log.d("Files", "FileName:" + file[i].getName());
        }

mY1=300;
        //canvas.drawPath(mPath, mPaint);

    }


    private void startTouch(float x1, float y1) {

        int x2=(int) x1;
        int y2=(int) y1;

        int x3 = (int)x1;
        int y3 = (int)y1;


        for (int ii=0;ii<=sp;ii++){


            if (rec[ii].contains(x2,y2)){

                Intent intent= new Intent( context, DrawActII.class);

                intent.putExtra("nam", file[ii].getName());

                context.startActivity(intent);

            }
            if (rec1[ii].contains(x2,y2)){

                File f =new File("/sdcard/smslo/"+file[ii].getName());
               f.delete();

            }


        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                startTouch(x,y);

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:


                break;
        }
        return true;
    }
}