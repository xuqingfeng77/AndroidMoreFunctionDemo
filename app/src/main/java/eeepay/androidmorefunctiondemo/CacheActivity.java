package eeepay.androidmorefunctiondemo;

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

import eeepay.androidmorefunctiondemo.util.CacheUtil;

public class CacheActivity extends AppCompatActivity {
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
        setContentView(R.layout.content_test);
        mEtxt=(EditText)findViewById(R.id.edtxt_amount);
        save=(Button)findViewById(R.id.save);
        get=(Button)findViewById(R.id.get);
        initData();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CacheUtil.readyToWriter(student,"cache.out");
                CacheUtil.readyToWriterInside(CacheActivity.this,student, "cache.out");
            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Student student2 = (Student) CacheUtil.readyToReaderInside(CacheActivity.this,Class.forName("eeepay.androidmorefunctiondemo.Student"), "cache.out");

                    Toast.makeText(CacheActivity.this, student2.getName(), Toast.LENGTH_SHORT).show();
                    Log.e("=========", student2.getName());
                } catch (Exception e) {

                }

            }
        });

/**
 *  设置小数位数控制
 */
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("//.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        };
//        mEtxt.addTextChangedListener(mTextWatcher);
//        mEtxt.setFilters(new InputFilter[]{lengthfilter});
        setEditListener(mEtxt);
//     Intent intent=new Intent(this, CameraWeviewAct.class);
//         startActivity(intent);

    }


    private void initData()
    {
        student = new Student();
        student.setName("@#$%^&*!~//////删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回");
        student.setOld("删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回");
        student.setAccountName("删除等特殊字符，删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回删除等特殊字符，直接返回");
        student.setAccountNo(" 删除等特殊字符，直接返回dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd. student. student. student. student. student. student.");
        student.setAcqName(" student. student. student. student. student. student. student. student. student.");
        student.setAcqResponseMsg(" 删除等特殊字符，直接返回. student. dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd. student. student.");
        student.setAlipayAmount(" student. student. student. student. student. student. student. student.");
        student.setAmount(" student. 删除等特殊字符，直接返回. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student. student.");

    }
    private Student student;
    private Student stu1;
    private File sdCardDir;
    private File sdFile;

    /**
     *
     * @param cacheObj
     * @param cachename  cache.out
     *  特别说明：混淆的时候就要注意不要混淆了泛型
     */
    private void readyToWriter(Object cacheObj,String cachename)
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            sdCardDir = Environment.getExternalStorageDirectory();
            sdFile = new File(sdCardDir, cachename);//输出到根目录
            try
            {
                FileOutputStream fos = new FileOutputStream(sdFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(cacheObj);
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param packagename   Class.forName("eeepay.androidmorefunctiondemo.Student")
     * @param cachename   "cache.out"
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * 特别说明：混淆的时候就要注意不要混淆了泛型
     */
    private <T extends Object> T readyToReader(Class<T> packagename,String cachename) throws InstantiationException,IllegalAccessException

    { Object  catchObj=null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        { sdCardDir = Environment.getExternalStorageDirectory();
            sdFile = new File(sdCardDir, cachename);//输出到根目录
            try
            {
                FileInputStream fis = new FileInputStream(sdFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                catchObj =  ois.readObject();
                fis.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return (T) catchObj;
    }
    private void readyToWriter2()
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            sdCardDir = Environment.getExternalStorageDirectory();
            sdFile = new File(sdCardDir, "student.out");//输出到根目录
            try
            {
                FileOutputStream fos = new FileOutputStream(sdFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(student);
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void readyToReader2()
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        { sdCardDir = Environment.getExternalStorageDirectory();
            sdFile = new File(sdCardDir, "student.out");//输出到根目录
            try
            {
                FileInputStream fis = new FileInputStream(sdFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                stu1 = (Student) ois.readObject();
                fis.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Toast.makeText(CacheActivity.this,stu1.getAmount(),Toast.LENGTH_SHORT).show();
        Log.e("=========", stu1.getName());
    }


    /**
     * 控制输入金额监听 控制输入形式为形式12,12.0,12.00
     *配合EditText android:numeric="decimal"使用
     * @param mEdTxtPayMoney
     */
    public static void setEditListener(final EditText mEdTxtPayMoney) {

        mEdTxtPayMoney.addTextChangedListener(new TextWatcher() {
            String temp2;
            boolean setTextFlag = true;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Log.i("TAG", "onTextChanged >>>>s=" + s + "start=" + start
                        + "before=" + before + "count=" + count);
                // s.subSequence(count-1, count);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                Log.i("TAG", "beforeTextChanged >>>>s=" + s + "start=" + start
                        + "after=" + after + "count=" + count);
                // if((s.length()+after)>9)//粘贴的时候超过限制长度，则不变化
                // {
                // mEdTxtPayMoney.setText(s);
                // setTextFlag=false;//
                // }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("TAG", "afterTextChanged" + s);
                if (setTextFlag) {
                    String str = mEdTxtPayMoney.getText().toString();
                    if (str.length() == 1)// 不能以小数点开头
                    {
                        if (str.substring(0).equals(".")) {
                            mEdTxtPayMoney.setText("");
                        }
                    } else// 判断只有一个小数点，并且小数的后最多有两位数字
                    {
                        int length = str.length();
                        int pointNumber = 0;// 小数点
                        int number = 0;// 小数点后的位数
                        boolean startRecordNumber = false;
                        for (int i = 0; i < length; i++) {

                            String temp3 = str.substring(i, i + 1);
                            if (temp3.equals(".")) {
                                if (i == 0) {
                                    String temp4 = str.substring(1);
                                    mEdTxtPayMoney.setText(temp4);
                                    mEdTxtPayMoney.setSelection(temp4.length());
                                    return;
                                }
                                pointNumber++;
                                startRecordNumber = true;
                            }
                            if (startRecordNumber) {
                                number++;
                            }
                        }
                        if (pointNumber >= 2) {
                            mEdTxtPayMoney.setText(temp2);
                            mEdTxtPayMoney.setSelection(temp2.length());
                            return;
                        }
                        if (number > 3) {
                            mEdTxtPayMoney.setText(temp2);
                            mEdTxtPayMoney.setSelection(temp2.length());
                            return;
                        }

                    }
                    temp2 = str;
                }

            }
        });

    }
}
