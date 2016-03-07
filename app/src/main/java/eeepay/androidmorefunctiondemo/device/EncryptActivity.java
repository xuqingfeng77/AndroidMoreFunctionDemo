/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eeepay.androidmorefunctiondemo.device;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.example.android.encryptactivity.encryptService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import eeepay.androidmorefunctiondemo.R;

/**
 * A minimal "Hello, World!" application.
 */
public class EncryptActivity extends Activity {
	/**
	 * Called with the activity is first created.
	 */

	static encryptService mkmyService;
	private EditText etAccout = null;
	private TextView tvInfo = null;
	private Button mBtn_out;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			requestPermission();
		} catch (InterruptedException e) {
		} catch (IOException e) {
		}

		mkmyService = new encryptService(this);

		// Set the layout for this activity. You can find it
		// in res/layout/hello_activity.xml
		View view = getLayoutInflater().inflate(R.layout.hello_activity, null);
		setContentView(view);

		StringBuffer verdata = new StringBuffer("");
		StringBuffer pindata = new StringBuffer("");
		mBtn_out = (Button) findViewById(R.id.btn_out_card);
		tvInfo = (TextView) findViewById(R.id.msg);

		etAccout = (EditText)findViewById(R.id.accout);

//		etAccout.setOnKeyListener(new EmailOnKeyListener());
//		mBtn_out.setOnKeyListener(new EmailOnKeyListener());
		mBtn_out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mkmyService.Encrypt_rejectcardOut();

			}
		});

		// mkmyService.GetVersionData(0,verdata);
		Log.i("--KMY----", "version:" + verdata.toString());

		
	}

	private class EmailOnKeyListener implements OnKeyListener {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_UP: //
				String sAccout = etAccout.getText().toString();
				etAccout.setText(Subs(sAccout));

				final int Code = event.getKeyCode();
				if (Code == KeyEvent.KEYCODE_NUM_LOCK)// 点击确认键，通知app可以获取密码
				{
					Log.i("--KMY----", "++++获取到密码++++++++");
					StringBuffer psdata = new StringBuffer("");
					mkmyService.GetPassWordData(psdata);
					Log.i("--KMY----", "psdata: " + psdata.toString());
					tvInfo.setText("密码1：" + psdata.toString());

					Log.i("--KMY----", "++++执行弹卡操作++++++++");
					Toast.makeText(EncryptActivity.this, "弹卡操作",
							Toast.LENGTH_LONG).show();
					mkmyService.Encrypt_rejectcardOut();
				} else if (Code == KeyEvent.KEYCODE_F12)// 获取磁道信息
				{
					StringBuffer cardnum = new StringBuffer("");
					mkmyService.GetCardNumberData(cardnum);

					Log.i("--KMY----", "cardnum:" + cardnum.toString());
					tvInfo.setText("获取磁道信息：" + cardnum.toString());
				} else if (Code == KeyEvent.KEYCODE_MEDIA_EJECT)// 弹卡
				{
					Log.i("--KMY----", "reject card out");
					mkmyService.Encrypt_rejectcardOut();

					Toast.makeText(EncryptActivity.this, "弹卡操作",
							Toast.LENGTH_LONG).show();
				} else if (Code == KeyEvent.KEYCODE_K) {
					StringBuffer pindata = new StringBuffer("");
					Log.i("--KMY----", "GetPinData");
					mkmyService.GetPinData(pindata);
					Log.i("--KMY----", "pindata:" + pindata.toString());
					tvInfo.setText("密码2：" + pindata.toString());
				} else if (Code == KeyEvent.KEYCODE_O) {
					Log.i("--KMY----", "input password time out!!!");
					Toast.makeText(EncryptActivity.this, "超时",
							Toast.LENGTH_LONG).show();
				} else if (Code == KeyEvent.KEYCODE_F1) {
					Log.i("--KMY----", "input password ");
					tvInfo.setText("输入密码中：" + System.currentTimeMillis());
				} else if (Code == KeyEvent.KEYCODE_F2) {
					Log.i("--KMY----", "input password time out!!!");
					tvInfo.setText("删除密码中：" + System.currentTimeMillis() + "删除");
				} else if (Code == KeyEvent.KEYCODE_F3) {
					Log.i("--KMY----", "input password time out!!!");
					tvInfo.setText("执行取消操作，自动执行退卡" + System.currentTimeMillis());
				}

			case KeyEvent.ACTION_DOWN: // ���̰���
				break;
			}
			return false;
		}

		private String Subs(String total) {
			String news = "";
			for (int i = 0; i <= total.length() / 4; i++)
				// �ֶκ���󲻼��м����-
				if (i * 4 + 4 < total.length())
					news = news
							+ total.substring(i * 4,
									Math.min(i * 4 + 4, total.length())) + "-";
				else
					news = news
							+ total.substring(i * 4,
									Math.min(i * 4 + 4, total.length()));
			return news;
		}
	}

	static void requestPermission() throws InterruptedException, IOException {
		createSuProcess("chmod 666 /dev/ttyS1").waitFor();
	}

	static Process createSuProcess() throws IOException {
		File rootUser = new File("/system/xbin/ru");
		if (rootUser.exists()) {
			return Runtime.getRuntime().exec(rootUser.getAbsolutePath());
		} else {
			return Runtime.getRuntime().exec("su");
		}
	}

	static Process createSuProcess(String cmd) throws IOException {

		DataOutputStream os = null;
		Process process = createSuProcess();

		try {
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit $?\n");
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}

		return process;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_1:
				Log.i("--KMY----", "++++1111++++++++");
				Toast.makeText(EncryptActivity.this,"1111111111",Toast.LENGTH_LONG).show();

				break;
			case KeyEvent.KEYCODE_ENTER:
				Log.i("--KMY----", "++++KEYCODE_ENTER++++++++");
				Toast.makeText(EncryptActivity.this,"KEYCODE_ENTER",Toast.LENGTH_LONG).show();

				break;


		}



		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mkmyService.Encrypt_close();
	}
}
