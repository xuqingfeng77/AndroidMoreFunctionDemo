package eeepay.androidmorefunctiondemo.intentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eeepay.androidmorefunctiondemo.MainActivity;
import eeepay.androidmorefunctiondemo.R;

public class MoreIntentActivity extends AppCompatActivity {
Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_intent);

    }
    public void BtnClick(View v){
        //具体例子可以看，我fork的AppScheme，这步操作可以调用起AppScheme
        mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("andy://scheme_activity?type=0&buffer=这是个字符串"));
        MoreIntentActivity.this.startActivity(mIntent);
    }
}
