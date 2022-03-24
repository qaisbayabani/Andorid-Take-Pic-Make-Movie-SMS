package com.example.bongi;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


/** Called when the activity is first created. */

public class DrawActII extends Activity {
    // Our variables
    CameraPreview cv;
    DrviII drv1;
    FrameLayout alParent;
    LinearLayout gr1;
    GridLayout gr2;
    // public this_canvas this_canvas;
    public Canvas this_canvas;

    String name,number,value,value1;

    EditText e1;

    Button b1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        e1 = new EditText(this);


        Bundle b =  getIntent().getExtras();

        if(b != null) {

            value1 = b.getString("nam");
            //e1.setText(value1);

        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Intent iin= new Intent(DrawActII.this ,DrawActivity.class);
        //Intent iin= getIntent();

    }



    public void Load(){




        // Try to get the camera
        Camera c = getCameraInstance();

        // If the camera was received, create the app
        if (c != null){
            // Create our layout in order to layer the
            // draw view on top of the camera preview.


            gr1=new LinearLayout(this);
            gr2=new GridLayout(this);
            gr2.setColumnCount(4);
            gr2.setRowCount(6);



            alParent = new FrameLayout(this);

            gr1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));

            gr2.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));

            alParent = new FrameLayout(this);


            b1=new Button(this);
            b1.setText("Send");
            b1.setWidth(23);
            b1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));



            alParent.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));


            // Create a new camera view and add it to the layout


            cv = new CameraPreview(this,c);

            // dv = new CanvasView(this);
            drv1=new DrviII(this,value1);


            drv1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            ScrollView scrollView = new ScrollView(this);

            scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            HorizontalScrollView h_scroll_view = new HorizontalScrollView(this);
            h_scroll_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


           // Bitmap mBitmap = Bitmap.createBitmap(630, 870, Bitmap.Config.ARGB_8888);
            //this_canvas = new Canvas(mBitmap);




            scrollView.addView(h_scroll_view);

            h_scroll_view.addView(drv1);

            gr1.setOrientation(LinearLayout.VERTICAL);

            //scrollView.scrollTo(700,10);

            alParent.addView(cv);

            alParent.addView(scrollView);

            //alParent.addView(dv);


            e1.setTextColor(Color.RED);
            e1.setHint("Type Your Message...");
            e1.setHintTextColor(Color.RED);
            e1.setEms(15);
            e1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));



            GridLayout.Spec row = GridLayout.spec(3 , 1);
            GridLayout.Spec colspan = GridLayout.spec(0 , 1);
            GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            gr2.addView(b1,gridLayoutParam);


            row = GridLayout.spec(3 , 1);
            colspan = GridLayout.spec(1 , 1);
            gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
            gr2.addView(e1,gridLayoutParam);


            gr1.addView(alParent);

            setContentView(gr1);

            //   LayoutInflater inflater = getLayoutInflater();


            LayoutInflater inflater = getLayoutInflater();

            getWindow().addContentView(gr2,

                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        }
        // If the camera was not received, close the app
        else {
            Toast toast = Toast.makeText(getApplicationContext(),"Unable to find camera. Closing", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                value = e1.getText().toString();

                //value1 = et1.getText().toString();

                sendSMS1(value1, value);

                //dv.getstr(name, number);

                //dv.invalidate();




                try {

                    //   showDialog(name,number,value);
                    //File sdcard = Environment.getExternalStorageDirectory();
//Get the text file
                    File f1 = new File("/sdcard/smslo/"+value1);
//Read text from file
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
                        //You'll need to add proper error handling here
                    }


                    File myFile = new File("/sdcard/smslo/"+value1);

                    FileOutputStream fOut = new FileOutputStream(myFile);

                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);


                    if (myFile.exists()) {


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
                    Log.e("ERRR", "Could not create file", e);
                }


            }
        });//end of butcli

    }



    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open();// attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    // Override the onPause method so that we
    // can release the camera when the app is closing.

    @Override
    protected void onPause() {
        super.onPause();


    }

    // We call Load in our Resume method, because
    // the app will close if we call it in onCreate
    @Override
    protected void onResume(){
        super.onResume();
        Load();
    }


    private void sendSMS1(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            Intent iin2= new Intent(DrawActII.this ,DrawActivity.class);
            startActivity(iin2);

            // your code
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}




