package com.example.android.fastaid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    EditText pas, usr;
    //Here in MainActivity we will write code for asking permission
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
   String[] nolist = {
            "+919355712808",
            "+919654284155",
            "+919729729273",
            "+919810871554",
            "+9198786449978"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usr = (EditText) findViewById(R.id.username);
        pas = (EditText) findViewById(R.id.password);
        //check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            //if the permission is not been granted then check if the user has denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                //Do nothing as user has denied
            } else {
                //a pop up will appear asking for required permission i.e Allow or Deny
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Button clickButton = (Button) findViewById(R.id.logBtn);
                clickButton.performClick();
            }
        }, 5000);
    }//onCreate

    //after getting the result of permission requests the result will be passed through this method
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //will check the requestCode
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                //check whether the length of grantResults is greater than 0 and is equal to PERMISSION_GRANTED
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Now broadcastreceiver will work in background
                    Toast.makeText(this, "Thankyou for permitting!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Well I can't do anything until you permit me", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean checkNo() {
        int lop;
        boolean condition = false;
        for (lop = 0; lop < 5; lop++) {
            if (nolist[lop].equals(MyReceiver.phoneNo)) {
                condition = true;
            }
        }
        return condition;
    }

    public void loginBtn(View view) {
        if(checkNo()){
        String user = MyReceiver.phoneNo;
        String pass = MyReceiver.msg;
        TextView myTextView= (TextView) findViewById(R.id.textView);
        myTextView.setText(MyReceiver.msg);
        background bg = new background(this);
        bg.execute(user,pass);
    }
}}

