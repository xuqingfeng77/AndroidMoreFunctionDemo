package eeepay.androidmorefunctiondemo.keyboard;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import eeepay.androidmorefunctiondemo.R;

public class KeyboardAct extends ActBase {
    TextView mEdtxt1;
    EditText mEdtxt2;
    EditText mEdtxt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        bindWidget();
//        mEdtxt1.setOnFocusChangeListener(focusListener);
//        pw.setEdit((EditText) mEdtxt1);

        mEdtxt1.setOnClickListener(new View.OnClickListener() { // edittext获得焦点，并再次点击时
            public void onClick(View v) {
                pw.setEdit( mEdtxt1);
            }
        });
    }
    /**
     *
     */
    private void bindWidget() {
        mEdtxt1=(TextView)findViewById(R.id.virtual_key_edtxt_test);
        mEdtxt2=(EditText)findViewById(R.id.virtual_key_edtxt_test2);
        mEdtxt3=(EditText)findViewById(R.id.virtual_key_edtxt_test3);


    }
}
