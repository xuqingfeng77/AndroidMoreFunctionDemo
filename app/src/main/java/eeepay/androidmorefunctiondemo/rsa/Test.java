package eeepay.androidmorefunctiondemo.rsa;


public class Test {
	static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD1mecWBLMB1snW3J089PGK/yICyWRzXnheUuIHD756S9g9XT0QqeR2l8k8L946VnTWLm3QmtpkS32c2ejfarvVnzkuJrYZyGZivN2hswz+PRxwresR8n/8NQOJ9hu9XVURL24owRKICQg5pD3lqRVL0MFxW+BJB/BZn+uSUFQMIwIDAQAB";
	static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPWZ5xYEswHWydbcnTz08Yr/IgLJZHNeeF5S4gcPvnpL2D1dPRCp5HaXyTwv3jpWdNYubdCa2mRLfZzZ6N9qu9WfOS4mthnIZmK83aGzDP49HHCt6xHyf/w1A4n2G71dVREvbijBEogJCDmkPeWpFUvQwXFb4EkH8Fmf65JQVAwjAgMBAAECgYAW56OFiiqnoUBxqWGArddY/zJM0DtuBwFyyogJ4I4DGc+w6WEojK+h38YEtvIivq1mzC2xpr93WxL77dap/2pE8y1ss2OVN2aPHbSdkGMy/BDQn2USJbtr8CC1DIL1a7NPWWD8dN8yDf3lS0EILin38ZzLkepEyVQS27GigQREAQJBAP9Julqbmba5M4M0YAtsa0l0DCTszEijnPg3A4nychsKWPROovkZlNaksX9/W2rcE+3JmxDBIZI1TvlUCholZNMCQQD2SUIr7JG0CA2J7Hhl632JnSOFZ2wUhILxNjFt1h0TA+PuCoDYPQQRjZ00kCfDfqiod0jxvwp+ElJeBtqHqGlxAkAFDGAzCoCvrFnoblC36Rz2BuV2lXg0t4eTIQNg5vp6rmmz6xot8uOOmxMngk08f72lJid63VbcnVFCfPb2LWchAkEAzr20ZHbT4JKZ+tucPcIuwaQ9OzEUEy0hViat24vPIDU10o7SlbKyhaGhA4y3NG5QWgq4GubJggcTSYbrTtFaoQJBAOZNJRcTrm8AcTv0SBoo8REYXI+CjiwXwVTEJrAxx2Sc4t7zxsYBJTSTrRH9F1bQPEgwtDUnFRrhizZsNrBflOc=";

	public static void main(String[] args) throws Exception {
		test();
	}

	static void test() throws Exception {
		System.err.println("公钥加密——私钥解密");
		String source = "111111";
		System.out.println("\r加密前文字：\r\n" + source);
		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
//		System.out.println("加密后文字：\r\n" +ISOUtil.hexString(encodedData));
		byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,
				privateKey);
		String target = new String(decodedData);
		System.out.println("解密后文字: \r\n" + target);
	}
	
	public static void test2()
	{
		
		String md5=Md5.md5Str("111111");
		String base64=Base64.encode(md5);
	}

}
