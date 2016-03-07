package eeepay.androidmorefunctiondemo.rsa;


import eeepay.androidmorefunctiondemo.util.MyLogger;

/**
 * 
 * @author xuqingfeng
 * @date 2013-7-17
 * @function bpaybox 后期就用这套加密
 */
public class EncRSA {
	static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOD6yHoQUx3zzSEORrLNI/aBIJF+UHSwn5NfEeqXKdJPaSsw8skLYkRSC4EV8yHlq0LbbZyC9tL4Iapwqk31zETLUOdCDWV5w1nLPOUQEkPKZoALw/x/uOb6kdIPin+Y6x8ivTWgo+Okuc/bIChH+6C2jNUO7J1m78yz4k/n1RrfAgMBAAECgYA/p9V3bA0IaYX9t1tbld16DrkXUYUULzFHclvQY2rq3ZslJFlddwIb0Jb+tCYxzhjx9sIBybBqqthjsDMPkd90g6PCIDXpZS0jrM4lNMezU0nLVYacu1FFfPCsuwYHTRUVP6rcBA/P9zepDuHTCrjMM/q0uippjuBisKGQPWKJeQJBAPnU7qFej8RIZOONqvmRxWK/YX9Yk+nJJ9buK9CGIS9cVaP0cPUCbhH7uud6ksbWTXz0xevNFMLVEdi36Z9Gm6UCQQDmiMW57uKWpQnj+SSJxHVAeG759IKHFNvy6qEZzNLj0PCOHvY/epT3kjCHNEuKHys0h02Rja4JbPL569x9LGUzAkBMw4GDMdQI4pmlVb1IK+MzgUyK3YOtXPKR1uK9kskVjVB/LTecAOAjRG7ce0woLmbm0ysRVhVf/Cocpf3xw7dFAkBseCYwmgnbP8Qrk+pxOBLDRudQjI9t546l+Wfbr9f6gTDubfsHWzIEWrCirSRt1MSLbq48siEVJkIc/bE85EqpAkEAxpBBNwzS0IOPmKMim0KKrybYWxshiJAkn4V/IB0werPo8nuVWqtbixXbw2H8GimaAVlGV14qS7a9jRYiZuFZsQ==";
	static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDg+sh6EFMd880hDkayzSP2gSCRflB0sJ+TXxHqlynST2krMPLJC2JEUguBFfMh5atC222cgvbS+CGqcKpN9cxEy1DnQg1lecNZyzzlEBJDymaAC8P8f7jm+pHSD4p/mOsfIr01oKPjpLnP2yAoR/ugtozVDuydZu/Ms+JP59Ua3wIDAQAB";

	/**
	 * 加密
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String EncPass(String source) throws Exception

	{
		MyLogger.aLog().i("公钥加密——私钥解密");
		MyLogger.aLog().i("\r加密前文字：\r\n" + source);
		// publicKey=Session.getSession().getEnv().get("loginPubkeys");
		MyLogger.aLog().i("\r加密公钥：\r\n" + publicKey);
		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);

		MyLogger.aLog().i("加密后文字：\r\n" + RSAUtils.hexString(encodedData));
		byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,
				privateKey);
		String target = new String(decodedData);
		MyLogger.aLog().i("解密后文字: \r\n" + target);
		return RSAUtils.hexString(encodedData);
	}

	/**
	 * 解密
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String DecPass(String source) throws Exception

	{
		byte[] encdata = hexStringToBytes(source);
		byte[] decodedData = RSAUtils.decryptByPrivateKey(encdata, privateKey);
		String target = new String(decodedData);
		MyLogger.aLog().i("解密后文字: \r\n" + target);
		return target;
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

}
