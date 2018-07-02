package com.example.mebinjohnson.logmein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
////Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

//set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_login);
    }
}
