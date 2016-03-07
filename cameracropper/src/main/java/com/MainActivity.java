package com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hipay.com.camercropper.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity_main);
    }

    public void takePhote(View view) {
        startActivity(new Intent(this, TakePhoteActivity.class));
    }

}
