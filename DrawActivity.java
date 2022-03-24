package com.example.bongi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;


public class DrawActivity extends AppCompatActivity{

    //   android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);


    public String number,value,value1;



        private int imageWidth = 640;
        private int imageHeight = 480;
    private int frameRate = 30;

    private static final String TAG = "okokokookokoook";
    Camera c;
    Drvi drv1;
    FrameLayout framelayout1;
    LinearLayout linearlayout1;
    GridLayout gridlayout2;
    MediaRecorder mediaRecorder;
    Date d = new Date();

    private boolean isRecording = false;
    EditText e1;
    EditText et1;

    Button b1;
    Button pic;
    Button mov;

    CameraPreview cv;

    public static boolean forcapturebutton;
    {
        forcapturebutton = false;
    }
    private static final String[] REQUIRED_PERMISSIONS = new String[] {

            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.RECORD_AUDIO

    };

    private static final int REQUEST_CODE_PERMISSIONS = 10;

    private boolean allPermissionsGranted() {
        for(String permission: REQUIRED_PERMISSIONS) {
            Boolean granted = ContextCompat.checkSelfPermission(
                    getBaseContext(), permission) == PackageManager.PERMISSION_GRANTED;
            if (!granted) {
                return false;
            }
        }
        return true;
    }
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open(0);// attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }



        super.onCreate(savedInstanceState);

        c = getCameraInstance();

        imageWidth = c.getParameters().getPreviewSize().width;
        imageHeight = c.getParameters().getPreviewSize().height;
        frameRate = c.getParameters().getPreviewFrameRate();

        cv = new CameraPreview(this, c);

        int he =  Resources.getSystem().getDisplayMetrics().widthPixels;
        int wi =  Resources.getSystem().getDisplayMetrics().heightPixels;


        if (true){

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            cv.setLayoutParams(params);

            framelayout1 = new FrameLayout(this);

            linearlayout1=new LinearLayout(this);


            gridlayout2=new GridLayout(this);
            gridlayout2.setColumnCount(2);
            gridlayout2.setRowCount(15);
            gridlayout2.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            gridlayout2.setVisibility(View.VISIBLE);



            framelayout1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            linearlayout1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            drv1=new Drvi(this);

            scrollView = new ScrollView(this);
            scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            et1 = new EditText(this);
            et1.setHint("Enter phone");
            et1.setTextColor(Color.RED);
            et1.setHintTextColor(Color.RED);
            et1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            e1 = new EditText(this);
            e1.setTextColor(Color.RED);
            e1.setHint("Type Your Message..........");
            e1.setHintTextColor(Color.RED);
            e1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            b1=new Button(this);
            b1.setBackgroundColor(Color.TRANSPARENT);
            b1.setTextColor(Color.RED);
            b1.setText("SendSMS");
            b1.setTextSize(12);
            b1.setWidth(30);
            b1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            pic = new Button(this);
            pic.setBackgroundColor(Color.TRANSPARENT);
            pic.setTextColor(Color.RED);
            pic.setText("TAKEPICTURE");
            pic.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            pic.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            pic.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            mov = new Button(this);
            mov.setBackgroundColor(Color.TRANSPARENT);
            mov.setTextColor(Color.RED);
            mov.setText("Movie");
            drv1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            GridLayout.Spec row = GridLayout.spec(1 , 1);
            GridLayout.Spec colspan = GridLayout.spec(1 , 0);
            GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            gridlayout2.addView(pic,gridLayoutParam);




            row = GridLayout.spec(6 , 1);
            colspan = GridLayout.spec(1 , 1);
            gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            gridlayout2.addView(mov, gridLayoutParam);

            b1.setWidth(he*(25/100));
            row = GridLayout.spec(7 , 1);
            colspan = GridLayout.spec(1 , 1);
            gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            gridlayout2.addView(b1, gridLayoutParam);

            row = GridLayout.spec(8 , 1);
            colspan = GridLayout.spec(0 , 1);
            gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            //e1.setEms(18);
            gridlayout2.addView(e1,gridLayoutParam);

            int x3 = (he*75)/100;
            et1.setWidth(x3);
            row = GridLayout.spec(9 , 1);
            colspan = GridLayout.spec(0 , 1, 0f);
            gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            gridlayout2.addView(et1, gridLayoutParam);





            scrollView.addView(drv1);

            framelayout1.addView(scrollView);
            linearlayout1.addView(framelayout1);
            linearlayout1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            LayoutInflater inflater = getLayoutInflater();


            getWindow().addContentView(linearlayout1,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            getWindow().addContentView(gridlayout2,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        else {

            Toast toast = Toast.makeText(getApplicationContext(),"Unable to find camera. Closing", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }




        mov.setOnClickListener(v -> {

            if (isRecording) {

                try {

                    mediaRecorder.stop();

                }catch (Exception e){}

                releaseMediaRecorder();

                //cv.getHolder().lockCanvas();
                //c.lock();

                mov.setText("MAKEMOVIE");
                mov.setTextColor(Color.RED);
                isRecording = false;
                b1.setEnabled(true);
                b1.setTextColor(Color.RED);
                pic.setEnabled(true);
                pic.setTextColor(Color.RED);




            } else {

                if (prepareVideoRecorder()) {
                    try {
                        mediaRecorder.start();
                    }
                    catch (Exception e) {
                    }

                    mov.setText("STOPMOVIECAPTURE");
                    isRecording = true;
                    b1.setTextColor(Color.BLUE);
                    b1.setEnabled(false);
                    pic.setTextColor(Color.BLUE);
                    pic.setEnabled(false);
                    mov.setTextColor(Color.GREEN);


                } else {
                    // prepare didn't work, release the camera
                    releaseMediaRecorder();
                    // inform user
                }
            }
        });

        b1.setOnClickListener(v -> {
            value = e1.getText().toString();
            value1 = et1.getText().toString();
            sendSMS(value1, value);
            try {
                File f1 = new File("/sdcard/smslo/"+value1);
                StringBuilder t5 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f1));
                    String line;
                    while ((line = br.readLine()) != null) {
                        t5.append(line);
                        t5.append("\n");
                    }
                    br.close();
                } catch (IOException e) {
                }
                File myFile = new File("/sdcard/smslo/" + value1);
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                if(myFile.exists()) {
                    t5.append("\n");
                    t5.append("You Say...");
                    t5.append(value);
                    t5.append("  ");
                    myOutWriter.append(t5);
                    myOutWriter.close();
                    fOut.close();
                    e1.setText("");
                } else {
                    myFile.createNewFile();
                    myOutWriter.append(number);
                    myOutWriter.append("msg:");
                    myOutWriter.append(value);
                    myOutWriter.close();
                    fOut.close();
                    e1.setText("");
                }
            } catch (Exception e) {
                //Log.e("ERRR", "Could not create file", e);
            }
        });//end of butcli



        pic.setOnClickListener(v -> {

            takePicture("sdcard/pictures/taken.jpg");




        });//End of Button Click


        framelayout1.addView(cv,
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        File folder = new File("/sdcard/Pictures/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File folder1 = new File("/sdcard/smslo/Initialize.txt");
        if (!folder1.exists()) {
            folder1.mkdirs();
        }
        File folder2 = new File("/sdcard/GPSDATA/");
        if (!folder2.exists()) {
            folder2.mkdirs();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        if(mediaRecorder!=null) {
            releaseMediaRecorder();       // if you are using MediaRecorder, release it first
           // releaseCamera();
        }

        c.stopPreview();
        framelayout1 = null;

        //releaseCamera();
    }

    @Override
    protected void onResume(){

 super.onResume();

 Load();

 Log.d(TAG, "===================on resume=========================================");

    }

 ScrollView scrollView;

    @Override
    protected void onDestroy(){

        super.onDestroy();
        if(mediaRecorder!=null) {
            releaseMediaRecorder();       // if you are using MediaRecorder, release it first
    }


    }


    public void Load(){


    }

    boolean forpicbutton = false;


    private void releaseMediaRecorder(){
        if (mediaRecorder != null) {


            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            //c.lock();           // lock camera for later use
        }
    }
    private void releaseCamera(){

        if (c != null){
            //c.stopPreview();
            c.release();
            c = null;
        }

    }
    private boolean prepareVideoRecorder(){


        mediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder

        c.unlock();

        mediaRecorder.setCamera(c);
        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        // Step 4: Set output file
        d = new Date();
        mediaRecorder.setVideoEncodingBitRate(8000000);

        mediaRecorder.setOutputFile("/sdcard/pictures/"+"HD Video"+System.currentTimeMillis()+".MP4");

        //      mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //      mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //mediaRecorder.setVideoFrameRate(60);
        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(cv.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {

            mediaRecorder.prepare();

        } catch (IllegalStateException e) {
            //Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            //Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;

        }
        return true;
    }


    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);

    }

    public void takePicture(final String fileName) {
        Log.i(TAG, "Tacking picture");

        Camera.PictureCallback callback = new Camera.PictureCallback() {

            private String mPictureFileName = fileName;

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.i(TAG, "Saving a bitmap to file");
                Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);
                try {
                    FileOutputStream out = new FileOutputStream(mPictureFileName);
                    picture.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    picture.recycle();
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        c.takePicture(null, null, callback);
    }




}









