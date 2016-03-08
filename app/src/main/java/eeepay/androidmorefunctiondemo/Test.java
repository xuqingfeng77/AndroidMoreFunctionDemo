package eeepay.androidmorefunctiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import eeepay.androidmorefunctiondemo.html.CameraWeviewAct;
import eeepay.androidmorefunctiondemo.util.CacheUtil;

public class Test extends AppCompatActivity {
    private static final int DECIMAL_DIGITS = 1;
    EditText mEtxt;
    Button save;
    Button get;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//test
     Intent intent=new Intent(this, CameraWeviewAct.class);
         startActivity(intent);
        //test
        //test
        //test
        //test
        //test
        //test
        //test

    }

}
