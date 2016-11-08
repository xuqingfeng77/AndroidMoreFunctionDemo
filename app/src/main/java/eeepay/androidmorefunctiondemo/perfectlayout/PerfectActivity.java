package eeepay.androidmorefunctiondemo.perfectlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import eeepay.androidmorefunctiondemo.R;

/**
 * 优化布局
 * add by xqf
 * 2016-11-08
 */
public class PerfectActivity extends AppCompatActivity {
Context mContext;
    TextView txtSpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect);
        mContext=PerfectActivity.this;
        txtSpan=(TextView)findViewById(R.id.txtspan);
        setTextColor();
    }
    private void setTextColor(){
        String text = String.format("￥%1$s  门市价:￥%2$s", 18.6, 22);
        int z = text.lastIndexOf("门");
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(dp2px(mContext,14)), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#afafaf")), z, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        style.setSpan(new AbsoluteSizeSpan(dp2px(mContext,14)), z, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号

        txtSpan.setText(style);
    }

    /**
     * dp转 px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int dp2px(Context context,float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }
}
