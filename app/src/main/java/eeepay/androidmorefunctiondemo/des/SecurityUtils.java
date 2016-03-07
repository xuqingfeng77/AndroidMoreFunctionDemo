/***********************************************************************
 * Module:  SecurityUtils.java
 * Author:  Administrator
 * Purpose: Defines the Class SecurityUtils
 * 一、数据补位

DES数据加解密就是将数据按照8个字节一段进行DES加密或解密得到一段8个字节的密文或者明文，最后一段不足8个字节，按照需求补足8个字节（通常补00或者FF，根据实际要求不同）进行计算，之后按照顺序将计算所得的数据连在一起即可。

这里有个问题就是为什么要进行数据补位？主要原因是DES算法加解密时要求数据必须为8个字节。

 

二、ECB模式

DES ECB（电子密本方式）其实非常简单，就是将数据按照8个字节一段进行DES加密或解密得到一段8个字节的密文或者明文，最后一段不足8个字节，按照需求补足8个字节进行计算，之后按照顺序将计算所得的数据连在一起即可，各段数据之间互不影响。

 

三、CBC模式

DES CBC（密文分组链接方式）有点麻烦，它的实现机制使加密的各段数据之间有了联系。其实现的机理如下：

 

加密步骤如下：

1）首先将数据按照8个字节一组进行分组得到D1D2......Dn（若数据不是8的整数倍，用指定的PADDING数据补位）

2）第一组数据D1与初始化向量I异或后的结果进行DES加密得到第一组密文C1（初始化向量I为全零）

3）第二组数据D2与第一组的加密结果C1异或以后的结果进行DES加密，得到第二组密文C2

4）之后的数据以此类推，得到Cn

5）按顺序连为C1C2C3......Cn即为加密结果。

 

解密是加密的逆过程，步骤如下：

1）首先将数据按照8个字节一组进行分组得到C1C2C3......Cn

2）将第一组数据进行解密后与初始化向量I进行异或得到第一组明文D1（注意：一定是先解密再异或）

3）将第二组数据C2进行解密后与第一组密文数据进行异或得到第二组数据D2

4）之后依此类推，得到Dn

5）按顺序连为D1D2D3......Dn即为解密结果。

这里注意一点，解密的结果并不一定是我们原来的加密数据，可能还含有你补得位，一定要把补位去掉才是你的原来的数据。
 ***********************************************************************/

package eeepay.androidmorefunctiondemo.des;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 3DES加解密算法，刷卡器中这个类功能比较全面，CBC和EBC模式
 * 
 * @author Administrator 内容多次加密处理的内容就有多长
 */
public class SecurityUtils {
	public final static String DES_TYPE = "DES/ECB/NoPadding";

	/**
	 * 
	 * 3倍长密钥加密 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
	 */
	public static byte[] encryptoCBCKey3(byte[] text, byte[] password) {
		if (password.length != 24)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] key3 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		System.arraycopy(password, 16, key3, 0, 8);
		byte[] result = encryptoCBCKey1(text, key1);
		result = decryptCBCKey1(result, key2);
		result = encryptoCBCKey1(result, key3);
		return result;
	}

	/**
	 * 
	 * 3倍长密钥解密 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
	 */
	public byte[] decryptCBCKey3(byte[] text, byte[] password) {
		if (password.length != 24)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] key3 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		System.arraycopy(password, 16, key3, 0, 8);
		byte[] result = decryptCBCKey1(text, key3);
		result = encryptoCBCKey1(result, key2);
		result = decryptCBCKey1(result, key1);
		return result;
	}

	/**
	 * 
	 * 双倍长密钥加密 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
	 * 
	 * K1=K3，但不能K1=K2=K3（如果相等的话就成了DES算法了）
	 */
	public static byte[] encryptoCBCKey2(byte[] text, byte[] password) {
		if (password.length != 16)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		byte[] result = encryptoCBCKey1(text, key1);
		result = decryptCBCKey1(result, key2);
		result = encryptoCBCKey1(result, key1);
		return result;
	}

	/**
	 * 
	 * 双倍长密钥解密 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
	 * 
	 * K1=K3，但不能K1=K2=K3（如果相等的话就成了DES算法了）
	 */
	public byte[] decryptCBCKey2(byte[] text, byte[] password) {
		if (password.length != 16)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		byte[] result = decryptCBCKey1(text, key1);
		result = encryptoCBCKey1(result, key2);
		result = decryptCBCKey1(result, key1);
		return result;
	}

	/**
	 * DES CBC加密
	 * 
	 * @return String
	 * @roseuid 4DF71E7A02AF
	 */
	public static byte[] encryptoCBCKey1(byte[] text, byte[] password) {
		if (password.length != 8)
			return null;
		byte[] result = new byte[text.length % 8 == 0 ? text.length
				: (text.length / 8 + 1) * 8];

		// 构造消息摘要
		int blockNum = result.length / 8;
		byte[] checkBlock = new byte[8];

		System.arraycopy(text, 0, checkBlock, 0, 8);
		byte[] temp = encryptoECB(checkBlock, password);
		System.arraycopy(temp, 0, result, 0, 8);

		for (int i = 1; i < blockNum; i++) {
			if (text.length < i * 8 + 8) {
				Arrays.fill(checkBlock, (byte) 0x00);
				System.arraycopy(text, i * 8, checkBlock, 0, text.length % 8);
			} else {
				System.arraycopy(text, i * 8, checkBlock, 0, 8);
			}

			for (int j = 0; j < 8; j++) {
				temp[j] = (byte) (temp[j] ^ checkBlock[j]);
			}
			temp = encryptoECB(temp, password);
			System.arraycopy(temp, 0, result, i * 8, 8);
		}
		return result;
	}

	/**
	 * DES CBC解密
	 * 
	 * @return String
	 * @roseuid 4DF71E7A02AF
	 */
	public static byte[] decryptCBCKey1(byte[] text, byte[] password) {
		if (password.length != 8)
			return null;
		byte[] result = new byte[text.length];
		// 构造消息摘要
		int blockNum = text.length / 8;

		byte[] checkBlock = new byte[8];
		System.arraycopy(text, 0, checkBlock, 0, 8);

		byte[] temp = decryptECB(checkBlock, password);
		System.arraycopy(temp, 0, result, 0, 8);

		for (int i = 1; i < blockNum; i++) {
			System.arraycopy(text, i * 8, checkBlock, 0, 8);
			temp = decryptECB(checkBlock, password);

			for (int j = 0; j < 8; j++) {
				temp[j] = (byte) (temp[j] ^ text[(i - 1) * 8 + j]);
			}
			System.arraycopy(temp, 0, result, i * 8, 8);
		}
		return result;
	}

	/**
	 * 
	 * 3倍长密钥加密 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
	 */
	public static byte[] encryptoECBKey3(byte[] text, byte[] password) {
		if (password.length != 24)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] key3 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		System.arraycopy(password, 16, key3, 0, 8);
		byte[] result = encryptoECB(text, key1);
		result = decryptECB(result, key2);
		result = encryptoECB(result, key3);
		return result;
	}

	/**
	 * 
	 * 3倍长密钥解密 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
	 */
	public static byte[] decryptECBKey3(byte[] text, byte[] password) {
		if (password.length != 24)
			return null;
		if (text.length % 8 > 0) {// 补足8字节整数倍
			byte[] temp = new byte[(text.length / 8 + 1) * 8];
			System.arraycopy(text, 0, temp, 0, text.length);
			text = temp;
		}
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		byte[] key3 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		System.arraycopy(password, 16, key3, 0, 8);
		byte[] result = decryptECB(text, key3);
		result = encryptoECB(result, key2);
		result = decryptECB(result, key1);
		return result;
	}

	/**
	 * 
	 * 双倍长密钥加密 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
	 * 
	 * K1=K3，但不能K1=K2=K3（如果相等的话就成了DES算法了）
	 */
	public static byte[] encryptoECBKey2(byte[] text, byte[] password) {

		if (text.length % 8 > 0) {// 补足8字节整数倍
			byte[] temp = new byte[(text.length / 8 + 1) * 8];
			System.arraycopy(text, 0, temp, 0, text.length);
			text = temp;
		}

		if (password.length != 16)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		byte[] result = encryptoECB(text, key1);
		result = decryptECB(result, key2);
		result = encryptoECB(result, key1);
		return result;
	}

	/**
	 * 
	 * 双倍长密钥解密 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
	 * 
	 * K1=K3，但不能K1=K2=K3（如果相等的话就成了DES算法了）
	 */
	public static byte[] decryptECBKey2(byte[] text, byte[] password) {
		if (password.length != 16)
			return null;
		// byte[] result = new
		// byte[text.length%8==0?text.length:(text.length/8+1)*8];
		byte[] key1 = new byte[8];
		byte[] key2 = new byte[8];
		System.arraycopy(password, 0, key1, 0, 8);
		System.arraycopy(password, 8, key2, 0, 8);
		byte[] result = decryptECB(text, key1);
		result = encryptoECB(result, key2);
		result = decryptECB(result, key1);
		return result;
	}

	/**
	 * DES加密过程
	 * 
	 * @param datasource
	 * @param password
	 * @return
	 */
	public static byte[] encryptoECB(byte[] datasource, byte[] password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES_TYPE);
			// 用密匙初始化Cipher对象 DES/ECB/NoPadding
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES解密过程
	 * 
	 * @param src
	 * @param password
	 * @return
	 */
	public static byte[] decryptECB(byte[] src, byte[] password) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(password);
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(DES_TYPE);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			// 真正开始解密操作
			return cipher.doFinal(src);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

}