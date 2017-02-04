/**
 * @author xuqingfeng
 * @date 2013-7-17
 * @function 
 */
package eeepay.androidmorefunctiondemo.des;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import eeepay.androidmorefunctiondemo.R;
import eeepay.androidmorefunctiondemo.md5.Md5;
import eeepay.androidmorefunctiondemo.rsa.Base64;
import eeepay.androidmorefunctiondemo.rsa.RSAUtils;
import eeepay.androidmorefunctiondemo.util.MyLogger;


/**
 * 加密
 * 
 * @author xuqingfeng
 * @date 2013-7-17
 * @function RSA,3DES,MD5,base64
 */
public class EncryptAct extends Activity implements OnClickListener {
	Button mBtn2Key;
	Button mBtnPubkey;
	Button mBtnPrikey;
	Button mBtn24key;
	Button mBtn24Deckey;
	Button mBtn16key;
	Button mBtn16Deckey;
	Button mBtn8key;
	Button mBtnMd5;
	Button mBtnBase64;
	Button mBtnBase64Dec;
	EditText mEtxtPubkey;
	EditText mEtxtPrikey;
	EditText mEtxtEncPubkey;
	EditText mEtxtEncPrikey;
	EditText mEtxt3Des24key;
	EditText mEtxt3Desdec24key;
	EditText mEtxt3Des16key;
	EditText mEtxt3Desdec16key;
	EditText mEtxt3Des8key;
	EditText mEtxtMd5;
	EditText mEtxtBase64Msg;
	EditText mEtxtBase64Enc;
	EditText mEtxtBase64Dec;
	Map<String, Object> map = null;
	String pri = null;
	String pub = null;
	byte[] encodedData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.encrypt_act);
		bindWidget();
	}

	/**
	 * 
	 */
	private void bindWidget() {
		mBtn2Key = (Button) findViewById(R.id.encrypt_act_btn_2key);
		mBtnPubkey = (Button) findViewById(R.id.encrypt_act_btn_pub);
		mBtnPrikey = (Button) findViewById(R.id.encrypt_act_btn_pri);
		mBtn24key = (Button) findViewById(R.id.encrypt_act_btn_24);
		mBtn24Deckey = (Button) findViewById(R.id.encrypt_act_btn_dec24);
		mBtn16key = (Button) findViewById(R.id.encrypt_act_btn_16);
		mBtn16Deckey = (Button) findViewById(R.id.encrypt_act_btn_dec16);
		mBtn8key = (Button) findViewById(R.id.encrypt_act_btn_8);
		mBtnMd5 = (Button) findViewById(R.id.encrypt_act_btn_md5);
		mBtnBase64 = (Button) findViewById(R.id.encrypt_act_btn_base64);
		mBtnBase64Dec = (Button) findViewById(R.id.encrypt_act_btn_base64_dec);
		mEtxtPubkey = (EditText) findViewById(R.id.encrypt_act_edtxt_pubkey);
		mEtxtPrikey = (EditText) findViewById(R.id.encrypt_act_edtxt_prikey);
		mEtxtEncPubkey = (EditText) findViewById(R.id.encrypt_act_edtxt_encpub);
		mEtxtEncPrikey = (EditText) findViewById(R.id.encrypt_act_edtxt_encpri);
		mEtxt3Des24key = (EditText) findViewById(R.id.encrypt_act_edtxt_24);
		mEtxt3Desdec24key = (EditText) findViewById(R.id.encrypt_act_edtxt_dec24);
		mEtxt3Des16key = (EditText) findViewById(R.id.encrypt_act_edtxt_16);
		mEtxt3Desdec16key = (EditText) findViewById(R.id.encrypt_act_edtxt_dec16);
		mEtxt3Des8key = (EditText) findViewById(R.id.encrypt_act_edtxt_8);
		mEtxtMd5 = (EditText) findViewById(R.id.encrypt_act_edtxt_md5);
		mEtxtBase64Msg = (EditText) findViewById(R.id.encrypt_act_edtxt_base64msg);
		mEtxtBase64Enc = (EditText) findViewById(R.id.encrypt_act_edtxt_base64_enc);
		mEtxtBase64Dec = (EditText) findViewById(R.id.encrypt_act_edtxt_base64_dec);
		mBtn2Key.setOnClickListener(this);
		mBtnPubkey.setOnClickListener(this);
		mBtnPrikey.setOnClickListener(this);
		mBtn24key.setOnClickListener(this);
		mBtn24Deckey.setOnClickListener(this);
		mBtn16key.setOnClickListener(this);
		mBtn16Deckey.setOnClickListener(this);
		mBtn8key.setOnClickListener(this);
		mBtnMd5.setOnClickListener(this);
		mBtnBase64.setOnClickListener(this);
		mBtnBase64Dec.setOnClickListener(this);

	}

	/*
	 * overriding methods
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.encrypt_act_btn_2key:

			try {
				map = RSAUtils.genKeyPair();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pri = RSAUtils.getPrivateKey(map);
				pub = RSAUtils.getPublicKey(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mEtxtPubkey.setText(pub);
			mEtxtPrikey.setText(pri);
			break;
		case R.id.encrypt_act_btn_pub:
			byte[] data = mEtxtEncPubkey.getText().toString().getBytes();
			try {
				encodedData = RSAUtils.encryptByPublicKey(data, pub);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String encstr = null;
			try {
				mEtxtEncPubkey.setText(RSAUtils.hexString(encodedData));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.encrypt_act_btn_pri:
			byte[] decodedData = null;
			try {
				decodedData = RSAUtils.decryptByPrivateKey(encodedData, pri);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mEtxtEncPrikey.setText(new String(decodedData));
			break;
		case R.id.encrypt_act_btn_24:
			enc3Des24key(0);
			break;
		case R.id.encrypt_act_btn_dec24:
			enc3Des24key(1);
			break;
		case R.id.encrypt_act_btn_16:
			enc3Des16key(0);
			break;
		case R.id.encrypt_act_btn_dec16:
			enc3Des16key(1);
			break;
		case R.id.encrypt_act_btn_8:
			enc3Des8key();
			break;
		case R.id.encrypt_act_btn_md5:
			encMd5();
			break;
			case R.id.encrypt_act_btn_base64:
				encBase64();
				break;
			case R.id.encrypt_act_btn_base64_dec:
				decBase64();
				break;


		}

	}

	private void enc3Des24key(int flag) {

		byte k24[] = FormatConversion
				.hexStringToBytes("111111111111111111111111111111112222222222222224");
		byte data24[] = FormatConversion
				.hexStringToBytes("111111111111111111111111111111112222222222222224");
		if (flag == 0) {
			mEtxt3Des24key.setText(FormatConversion
					.bytesToHexString(SecurityUtils
							.encryptoECBKey3(data24, k24)));
			MyLogger.aLog().i(
					"加密encryptoECBKey24="
							+ FormatConversion.bytesToHexString(SecurityUtils
									.encryptoECBKey3(data24, k24)));
		} else {
			mEtxt3Desdec24key.setText(FormatConversion
					.bytesToHexString(SecurityUtils.decryptECBKey3(
							SecurityUtils.encryptoECBKey3(data24, k24), k24)));
			MyLogger.aLog()
					.i("解密decryptECBKey24="
							+ FormatConversion.bytesToHexString(SecurityUtils
									.decryptECBKey3(SecurityUtils
											.encryptoECBKey3(data24, k24), k24)));
		}

	}

	private void enc3Des16key(int flag) {
		byte k16[] = FormatConversion
				.hexStringToBytes("11111111111111111111111111111116");
		byte data16[] = FormatConversion
				.hexStringToBytes("11111111111111111111111111111116");
		if (flag == 0) {
			mEtxt3Des16key.setText(FormatConversion
					.bytesToHexString(SecurityUtils
							.encryptoECBKey2(data16, k16)));
			MyLogger.aLog().i(
					"加密encryptoECBKey16="
							+ FormatConversion.bytesToHexString(SecurityUtils
									.encryptoECBKey2(data16, k16)));
		} else {
			mEtxt3Desdec16key.setText(FormatConversion
					.bytesToHexString(SecurityUtils.decryptECBKey2(
							SecurityUtils.encryptoECBKey2(data16, k16), k16)));
			MyLogger.aLog()
					.i("解密decryptECBKey16="
							+ FormatConversion.bytesToHexString(SecurityUtils
									.decryptECBKey2(SecurityUtils
											.encryptoECBKey2(data16, k16), k16)));
		}

	}

	private void enc3Des8key() {
		byte k8[] = FormatConversion.hexStringToBytes("1111111111111111");
		byte data8[] = FormatConversion.hexStringToBytes("1111111111111118");
		MyLogger.aLog().i(
				"加密encryptoECBKey8="
						+ FormatConversion.bytesToHexString(SecurityUtils
						.encryptoECBKey2(data8, k8)));
		MyLogger.aLog().i(
				"解密decryptECBKey8="
						+ FormatConversion.bytesToHexString(SecurityUtils
								.decryptECB(
										SecurityUtils.encryptoECB(data8, k8),
										k8)));
	}

	private void encMd5() {
		String md = mEtxtMd5.getText().toString();
		mEtxtMd5.setText(Md5.encode(md));

	}
	private void encBase64() {
		String md = mEtxtBase64Msg.getText().toString();
		mEtxtBase64Enc.setText(Base64.encode(md,"utf-8"));

	}
	private void decBase64() {
		String md = mEtxtBase64Enc.getText().toString();
		mEtxtBase64Dec.setText(Base64.decode(md,"utf-8"));

	}
}