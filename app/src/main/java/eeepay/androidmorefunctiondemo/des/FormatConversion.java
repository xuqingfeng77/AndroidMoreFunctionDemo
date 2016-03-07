package eeepay.androidmorefunctiondemo.des;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 格式转换
 * 
 * @author xuqingfeng
 * 
 */
public class FormatConversion {
	/**
	 * 例如 bytes=new bytes[0x01,0xEE,0x09] 转为string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		char[] temp1 = { 'A', 'B', 'C', 'D', 'E', 'F' };

		for (int i = 0; i < bytes.length; i++) {
			if (((bytes[i] & 0xf0) >> 4) <= 9) {
				temp.append((byte) ((bytes[i] & 0xf0) >> 4));
			} else {
				temp.append(temp1[((bytes[i] & 0xf0) >> 4) - 10]);
			}
			if ((bytes[i] & 0x0f) <= 9) {
				temp.append((byte) (bytes[i] & 0x0f));
			} else {
				temp.append(temp1[(bytes[i] & 0x0f) - 10]);
			}
		}
		return temp.toString();
	}

	/**
	 * string转为byte【】类型
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/** */
	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/** */
	/**
	 * 把字节数组转换为对象
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static final Object bytesToObject(byte[] bytes) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(in);
		Object o = oi.readObject();
		oi.close();
		return o;
	}

	/** */
	/**
	 * 把可序列化对象转换成字节数组
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static final byte[] objectToBytes(Serializable s) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream ot = new ObjectOutputStream(out);
		ot.writeObject(s);
		ot.flush();
		ot.close();
		return out.toByteArray();
	}

	public static final String objectToHexString(Serializable s)
			throws IOException {
		return bytesToHexString(objectToBytes(s));
	}


	/** */
	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2DeStr(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/** */
	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	/**
	 * 字符形式 153.89
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] Str2Byte(String st) {
		String str = st;
		if (str.contains(".")) {

			String s = "\\.";
			String[] re = str.split(s);
			System.out.println("re[0]" + re[0]);
			System.out.println("re[1]" + re[1]);
			if (re[1].length() == 1) {
				str = re[0] + re[1] + "0";
			} else

			{
				str = re[0] + re[1];
			}
			System.out.println("str" + str);
		} else {
			str = str + "00";
		}

		int strsLength = str.length();
		byte[] result = new byte[strsLength];
		for (int i = 0; i < strsLength; i++) {
			String s = str.substring(i, i + 1);
			if (s.equals("0")) {
				result[i] = (byte) 0x30;
			} else if (s.equals("1")) {
				result[i] = (byte) 0x31;
			} else if (s.equals("2")) {
				result[i] = (byte) 0x32;
			} else if (s.equals("3")) {
				result[i] = (byte) 0x33;
			} else if (s.equals("4")) {
				result[i] = (byte) 0x34;
			} else if (s.equals("5")) {
				result[i] = (byte) 0x35;
			} else if (s.equals("6")) {
				result[i] = (byte) 0x36;
			} else if (s.equals("7")) {
				result[i] = (byte) 0x37;
			} else if (s.equals("8")) {
				result[i] = (byte) 0x38;
			} else if (s.equals("9")) {
				result[i] = (byte) 0x39;
			} else {
				result[i] = (byte) 0x30;
			}
		}
		return result;
	}

	/**
	 * 156.00金额转为byte数字 转为格式[0x31,0x35,0x36,0x30,0x30]
	 * 
	 * @param money
	 */
	public static byte[] strMoney2byte(String money) {

		String str = money;
		if (str.contains(".")) {

			String s = "\\.";
			String[] re = str.split(s);
			System.out.println("re[0]" + re[0]);
			System.out.println("re[1]" + re[1]);
			if (re[1].length() == 1) {
				str = re[0] + re[1] + "0";
			} else

			{
				str = re[0] + re[1];
			}
			System.out.println("str" + str);
		} else {
			str = str + "00";
		}

		String bb = str2HexStr(str);
		System.out.println("-----bbb------" + bb);
		byte strby[] = new byte[bb.length() / 2];
		int j = 0;
		for (int i = 0; i < bb.length(); i += 2) {
			System.out.println("-----------" + (bb.substring(i, i + 2)));
			strby[j] = (byte) Integer.parseInt((bb.substring(i, i + 2)));
			j++;
		}
		return strby;
	}

	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		byte bb[] = new byte[str.length()];
		StringBuilder sb = new StringBuilder("");

		byte[] bs = str.getBytes();

		int bit;

		for (int i = 0; i < bs.length; i++) {

			bit = (bs[i] & 0x0f0) >> 4;

			sb.append(chars[bit]);

			bit = bs[i] & 0x0f;
			bb[i] = (byte) chars[bit];
			sb.append(chars[bit]);

		}

		return sb.toString();

	}

	/**
	 * 获取偶数为字符 "313141383938" 转为为11A898 31对于ascii值为1 ，41对于的ascii值为A
	 * 
	 * @return
	 */
	public static String stringCut(String str) {
		// if (new StringIsLawful().isStrEmpty(str)) {
		// return null;
		// }
		String result = "";
		char tempResult;
		int temp = 0;
		int temp1 = 0;
		for (int i = 0; i < str.length(); i++) {
			if (i % 2 == 0) {
				temp1 = Integer.parseInt(str.substring(i, i + 1)) * 16;
				temp += temp1;
			} else {
				temp1 = Integer.parseInt(str.substring(i, i + 1)) * 1;
				temp += temp1;
				tempResult = (char) temp;
				result += tempResult + "";
				temp = 0;

			}

		}

		return result;

	}
    /**
     * 8字节数据与0xffffffffffffffff异或运算
     */
	public static String xor8(String str) {
//		byte[] data0 = FormatConversion.hexStringToBytes("836c7cef00000010");//[-125, 108, 124, -17, 0, 0, 0, 16]
		byte[] data0 = FormatConversion.hexStringToBytes(str);
		byte[] data1 = new byte[data0.length];
		byte[] data2 = { -1 };
		for (int i = 0; i < data0.length; i++) {

			data1[i] = (byte) (data0[i] ^ data2[0]);//124, -109, -125, 16, -1, -1, -1, -17

		}
		String data3=FormatConversion.bcd2Str(data1);//7c......
		return data3;
		
	}
}
