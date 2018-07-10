package eeepay.androidmorefunctiondemo.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;


import eeepay.androidmorefunctiondemo.R;

/**
 * 
 * ??????????????
 * 
 * @author Ted ???????2011-12-19
 */
public class PopupWin {
	// inputTpye =0 表示20位模式，inputTpye =1 表示6位模式。
	public static int inputTpye = 0;
	Context context;

	static PopupWindow mPopupWindow;
	static TextView edit;
	static EditText et_kb;
	View popupView;
	VirtualKeyboard kbd;

	public void setEdit(TextView v) {
		edit = v;
		popOut();
		attachToEdit();
	}

	public PopupWin(Context context) {
		this.context = context;
		check();
	}

	public void popOut() {
		check();//add by xuqingfeng
		if (isShowing())
			return;
		dismissSysInputDlg();
		mPopupWindow.showAtLocation(
				((Activity) context).findViewById(R.id.virtual_key_btn_title),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		dismissSysInputDlg();
	}

	private void createPopupView() {
		popupView = LayoutInflater.from(context).inflate(
				R.layout.virtual_keyboard, null);

		et_kb = (EditText) popupView.findViewById(R.id.et);
		// 针对htc禁掉触摸事件
		et_kb.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		kbd = (VirtualKeyboard) popupView.findViewById(R.id.kbd);
		kbd.setListener(enterListener);
		kbd.bringToFront();
		kbd.setUp(et_kb);
	}

	private void attachToEdit() {
//		edit.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
//		edit.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
		et_kb.setText(edit.getText().toString());
		et_kb.setSelection(et_kb.length());
		et_kb.addTextChangedListener(watcher);
	}

	private static TextWatcher watcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			edit.setText(et_kb.getText().toString());
			// edit.setTransformationMethod(PasswordTransformationMethod
			// .getInstance());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
//			edit.setSelection(edit.length());
		}
	};

	private void check() {
		if (mPopupWindow == null) {
			if (popupView == null) {
				createPopupView();
			}
			int width = Act.getScreenWidth(context);
			int height = Act.getScreenHeight(context);

			Log4j.debug("width: " + width);
			Log4j.debug("height: " + height);

			if (height < 480) { // 小屏320*240
				mPopupWindow = new PopupWindow(popupView, width,
						height * 3 / 6 - 10);
			} else {
				if (height < 600) { // 小屏480*320
					mPopupWindow = new PopupWindow(popupView, width,
							height * 3 / 7 - 6);
				} else {// 大屏800*480,1280*720
					mPopupWindow = new PopupWindow(popupView, width,
							height * 3 / 8);
				}
			}
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(
					Color.TRANSPARENT));
			mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
			mPopupWindow.setOutsideTouchable(false);
			mPopupWindow.update();
		}

	}

	EnterBtnClickListener enterListener = new EnterBtnClickListener() {
		public void done() {
			disMiss();
			mPopupWindow = null;// add by xuqingfeng
			popupView = null;// add by xuqingfeng

		}
	};

	public static boolean isShowing() {
		if (mPopupWindow != null)
			return mPopupWindow.isShowing();
		return false;
	}

	public static void disMiss() {
		if (isShowing()) {
			et_kb.removeTextChangedListener(watcher);
			mPopupWindow.dismiss();
		}
	}

	public void dismissSysInputDlg() {
		Log4j.debug("------------------------------dismissSysInputDlg");
		((Activity) this.context).getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		InputMethodManager imm = (InputMethodManager) ((Activity) this.context)
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}

}
