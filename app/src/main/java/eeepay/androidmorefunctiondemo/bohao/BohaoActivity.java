package eeepay.androidmorefunctiondemo.bohao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import eeepay.androidmorefunctiondemo.R;
import eeepay.androidmorefunctiondemo.util.MathUtil;
import eeepay.androidmorefunctiondemo.util.MyLogger;

/**
 *@author : xqf
 *@date   :2019/4/1 上午12:31
 *@desc   :通讯记录分析；移动和联通的电话卡可以判断durtion如果大于0，则表示接通过，电信的需要另外处理
 * 电信的官方回复是：CDMA手机在发起呼叫时，只要占用无线信道，就开始计时，它计的是手机占用信道的时间。计费时间是按照用户实际开始通话的时间计算的，不是手机显示的时间。
 *@update :
 */
public class BohaoActivity extends Activity {
    private ListView lv;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bohao);
        initView();

    }
    private void initView(){
        lv = (ListView) findViewById(R.id.callView);
        ContactsMsgUtils contactsMsgUtils = new ContactsMsgUtils();
        List<CallLogInfo> infos = contactsMsgUtils.getCallLog(this);
        adapter = new MyAdapter(infos);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                CallLogInfo info = (CallLogInfo) adapter.getItem(arg2);
                final String number = info.number;
                String[] items = new String[] { "复制号码到拨号盘, 拨号, 发送短信 "};
                new AlertDialog.Builder(BohaoActivity.this).setTitle("操作")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        startActivity(new Intent(
                                                Intent.ACTION_DIAL, Uri
                                                .parse("tel:" + number)));
                                        break;
                                    case 1:
//                                        startActivity(new Intent(
//                                                Intent.ACTION_CALL, Uri
//                                                .parse("tel:" + number)));
                                        break;
                                    case 2:
                                        startActivity(new Intent(
                                                Intent.ACTION_SENDTO, Uri
                                                .parse("sms:" + number)));
                                        break;

                                    default:
                                        break;
                                }

                            }
                        }).show();

                return false;
            }
        });

    }
    private class MyAdapter extends BaseAdapter {
        private List<CallLogInfo> infos;
        private LayoutInflater inflater;

        public MyAdapter(List<CallLogInfo> infos) {
            super();
            this.infos = infos;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {

            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.call_log_item, null);
            TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            TextView tv_durtion = (TextView) view.findViewById(R.id.tv_durtion);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
            CallLogInfo info = infos.get(position);
            tv_number.setText(info.number);

            int callDuration=Integer.parseInt(info.durtion);
            int min=callDuration/60;
            int sec=callDuration%60;
            tv_durtion.setText("通话时长："+min+"分"+sec+"秒");


            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            String dateStr = format.format(info.date);
            tv_date.setText(dateStr);
            String typeStr = null;
            int color = 0;
            switch (info.type) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeStr = "来电";
                    color = Color.BLUE;

                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeStr = "去电";
                    color = Color.GREEN;

                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeStr = "未接";
                    color = Color.RED;

                    break;

                default:
                    break;
            }
            tv_type.setText(typeStr);
            tv_type.setTextColor(color);
            return view;
        }

    }


}
