package eeepay.androidmorefunctiondemo.keyboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import eeepay.androidmorefunctiondemo.R;


/**
 * 
 * 描述：界面基类
 * 
 * @author Ted 创建时间：2011-12-19
 */
public class ActBase extends Activity {

	PopupWin pw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pw = new PopupWin(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) { // 系统后退键销毁软键盘
			if (PopupWin.isShowing()) {
				PopupWin.disMiss();
				return true;
			} else {
				System.out.println();
				Log4j.debug("ActBase back key click.......................");
				return super.onKeyDown(keyCode, event);
			}
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			Log4j.debug("ActBase home key click.......................");
			return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	protected OnFocusChangeListener focusListener = new OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
//				if (((EditText) v).getId() == R.id.virtual_key_edtxt_test2
//						|| ((EditText) v).getId() == R.id.virtual_key_edtxt_test3) {
//					// System.out.println("set input type");
//					PopupWin.inputTpye = 1;
//				} else {
//					// System.out.println("reset input type");
//					PopupWin.inputTpye = 0;
//				}
//				pw.setEdit((EditText) v);
//
//				v.setOnClickListener(new OnClickListener() { // edittext获得焦点，并再次点击时
//					public void onClick(View v) {
//						pw.setEdit((EditText) v);
//					}
//				});
				pw.dismissSysInputDlg();
			} else { // 当edit失去焦点,销毁软键盘,如返回主菜单或确定按钮被点击
				PopupWin.disMiss();
			}
		}
	};

}