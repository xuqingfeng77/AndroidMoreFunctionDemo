package eeepay.androidmorefunctiondemo.des;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

public class DES33 {

	static String DES = "DES/ECB/NoPadding";
	static String TriDes = "DESede/ECB/NoPadding";

	public static byte[] des_crypt(byte key[], byte data[]) {

		try {
			KeySpec ks = new DESKeySpec(key);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
			SecretKey ky = kf.generateSecret(ks);

			Cipher c = Cipher.getInstance(DES);
			c.init(Cipher.ENCRYPT_MODE, ky);
			return c.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] des_decrypt(byte key[], byte data[]) {

		try {
			KeySpec ks = new DESKeySpec(key);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
			SecretKey ky = kf.generateSecret(ks);

			Cipher c = Cipher.getInstance(DES);
			c.init(Cipher.DECRYPT_MODE, ky);
			return c.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] trides_crypt(byte key[], byte data[]) {
		try {
			byte[] k = new byte[24];

			int len = data.length;
			if (data.length % 8 != 0) {
				len = data.length - data.length % 8 + 8;
			}
			byte[] needData = null;
			if (len != 0)
				needData = new byte[len];

			for (int i = 0; i < len; i++) {
				needData[i] = 0x00;
			}

			System.arraycopy(data, 0, needData, 0, data.length);

			if (key.length == 16) {
				System.arraycopy(key, 0, k, 0, key.length);
				System.arraycopy(key, 0, k, 16, 8);
			} else {
				System.arraycopy(key, 0, k, 0, 24);
			}

			KeySpec ks = new DESedeKeySpec(k);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
			SecretKey ky = kf.generateSecret(ks);

			Cipher c = Cipher.getInstance(TriDes);
			c.init(Cipher.ENCRYPT_MODE, ky);
			return c.doFinal(needData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static byte[] trides_decrypt(byte key[], byte data[]) {
		try {
			byte[] k = new byte[24];

			int len = data.length;
			if (data.length % 8 != 0) {
				len = data.length - data.length % 8 + 8;
			}
			byte[] needData = null;
			if (len != 0)
				needData = new byte[len];

			for (int i = 0; i < len; i++) {
				needData[i] = 0x00;
			}

			System.arraycopy(data, 0, needData, 0, data.length);

			if (key.length == 16) {
				System.arraycopy(key, 0, k, 0, key.length);
				System.arraycopy(key, 0, k, 16, 8);
			} else {
				System.arraycopy(key, 0, k, 0, 24);
			}
			KeySpec ks = new DESedeKeySpec(k);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
			SecretKey ky = kf.generateSecret(ks);

			Cipher c = Cipher.getInstance(TriDes);
			c.init(Cipher.DECRYPT_MODE, ky);
			return c.doFinal(needData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void des33() {

		// byte k1[] = { 0xFF, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11 };
		// byte k2[] = { 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11 };
		byte k16[] =  hexToBytes("111111111111111111111111111111112222222222222222");
		byte data[] = hexToBytes("22222222222222222222222222222222");
		System.out.println("DES33加密前数据："+byte2hex(data));
		// System.arraycopy(k1, 0, k16, 0, 8);
		// System.arraycopy(k2, 0, k16, 8, 8);

		// data = new byte[8];
		// byte des_crypt[] = des_crypt(k1, data);
		// byte des_descrypt[] = des_decrypt(k2, des_crypt);
		// byte result[] = des_crypt(k1, des_descrypt);

		byte result[] = trides_crypt(k16, data);

		System.out.println("DES33加密结果："+byte2hex(result));
		System.out.println("DES33解密结果："+byte2hex(trides_decrypt(k16, result)));
		System.out.println("Result = " + result.length);
	}

	public static String byte2hex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String temp = Integer.toHexString(((int) data[i]) & 0xFF);
			for (int t = temp.length(); t < 2; t++) {
				sb.append("0");
			}
			sb.append(temp);
		}
		return sb.toString();
	}

	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}
}
