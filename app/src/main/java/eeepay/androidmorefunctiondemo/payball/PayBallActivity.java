package eeepay.androidmorefunctiondemo.payball;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import eeepay.androidmorefunctiondemo.R;
import eeepay.androidmorefunctiondemo.util.MathUtil;
import eeepay.androidmorefunctiondemo.util.MyLogger;

/**
 * @author : xqf
 * @date :2018/11/17 下午7:06
 * @desc :玩球复投计划
 * @update :
 */
public class PayBallActivity extends Activity {
    EditText edtxtFirstAmount, edtxtPercent, edtxtResult,edtxtBlackNum;
    Button btnCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ball);
        initView();

    }
    private void initView(){
        edtxtFirstAmount=(EditText) findViewById(R.id.edtxt_firstamount);
        edtxtPercent=(EditText) findViewById(R.id.edtxt_percent);
        edtxtResult=(EditText) findViewById(R.id.edtxt_result);
        edtxtBlackNum=(EditText) findViewById(R.id.edtxt_black);
        btnCal=(Button) findViewById(R.id.btn_cal);
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcResult();
            }
        });
    }
    private void calcResult(){
        int blackNum=Integer.parseInt(edtxtBlackNum.getText().toString());
        double firstAmount=Double.parseDouble(edtxtFirstAmount.getText().toString());
        double percent=Double.parseDouble(edtxtPercent.getText().toString());
        List<Double> amountList=new ArrayList<>();
        amountList.add(firstAmount);
        MyLogger.aLog().d("第1"+"单"+"投注金额"+firstAmount+"元\n");
        StringBuffer result=new StringBuffer();
        result.append("此复投方案可以保证最后一次盈利为首次投注金额*赔率\n\n");
        result.append("第1"+"单"+"投注金额"+firstAmount+"元\n");

        for(int i=1;i<blackNum;i++){
            int amountListSize=amountList.size();
            double tempAmount= 0;
            for(int j=0;j<amountListSize;j++){
                tempAmount=tempAmount+amountList.get(j);
            }
            tempAmount=tempAmount+ MathUtil.multiply(firstAmount,0.85);
            double aaresult=MathUtil.divide(tempAmount,percent);
            MyLogger.aLog().d("第"+(i+1)+"单"+"投注金额"+aaresult+"元");
            result.append("第"+(i+1)+"单"+"投注金额"+aaresult+"元\n");
            amountList.add(aaresult);

        }
        edtxtResult.setText(result.toString());

    }

}
