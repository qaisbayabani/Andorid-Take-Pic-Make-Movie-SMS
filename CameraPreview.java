package com.example.bongi;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    public CameraPreview(Context context, Camera camera) {

        super(context);
        mCamera = camera;
        mCamera.setDisplayOrientation(90);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }
    public void surfaceDestroyed(SurfaceHolder holder) {


    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        try {
            mCamera.setPreviewDisplay(holder);
           mCamera.stopPreview();
            mCamera.startPreview();
        } catch (IOException e) {
            //Log.d("setting camera preview:"+ e.getMessage()"");
        }


    }

}
