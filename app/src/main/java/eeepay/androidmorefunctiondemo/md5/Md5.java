package eeepay.androidmorefunctiondemo.md5;

import java.security.MessageDigest;

import android.util.Log;

/**
 * 描述：MD5算法基础类
 *
 * @author  北京农商行方案
 * 创建时间：2009-4-2
 */

public class Md5 {
//  private static Log log = new Log(Md5.class);//disable by xuqingfeng

  private final static String[] hexDigits = { 
    "0", "1", "2", "3", "4", "5", "6", "7", 
    "8", "9", "a", "b", "c", "d", "e", "f"}; 
  
  /** 
   * 转换字节数组为16进制字串 
   * @param b 字节数组 
   * @return 16进制字串 
   */
  public static String byteArrayToHexString(byte[] b) { 
    StringBuffer resultSb = new StringBuffer(); 
    for (int i = 0; i < b.length; i++) { 
      resultSb.append(byteToHexString(b[i])); 
    } 
    return resultSb.toString(); 
  } 

  private static String byteToHexString(byte b) { 
	 
    int n = b; 
    if (n < 0) 
      n = 256 + n; 
    int d1 = n / 16; 
    int d2 = n % 16; 
    return hexDigits[d1] + hexDigits[d2]; 
  } 

  /**
   * 计算MD5
   * @param origin 原始字符串
   * @return md5字符串；null：出错
   */
  public static String encode(String origin) { 
    String resultString = null; 

    try { 
      resultString=new String(origin); 
      MessageDigest md = MessageDigest.getInstance("MD5"); 
      resultString=byteArrayToHexString(md.digest(resultString.getBytes())); 
    }catch (Exception ex) { 
//      log.logPrint("计算MD5出错", ex);//disable by xuqingfeng
    } 
    return resultString; 
  }
  
  /**
   * 验证MD5串
   * @param origin 原始字符串
   * @param md5 md5字符串
   * @return true：正确；false：不正确
   */
  public static boolean verify(String origin, String md5){
    String tmp = encode(origin);
    try{
      if (tmp.equals(md5)){
        return true;
      }
    }catch (Exception e){      
    }
    
    return false;
  }

}
