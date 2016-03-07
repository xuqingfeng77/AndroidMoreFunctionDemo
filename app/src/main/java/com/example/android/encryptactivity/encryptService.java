package com.example.android.encryptactivity;

import android.util.Config;
import android.util.Log;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.IBinder;

public final class encryptService {//extends IpicService.stub{

	//PIC 通讯控制命令
	private static final byte CMD_SET_CURRUNT_TIME = (byte) 0xA1; 
	private static final byte CMD_SET_SHUTDOWM_TIME = (byte)0xA2;
	private static final byte CMD_SET_START_TIME	= (byte)0xA3;
	
	private static final byte CMD_RTC_CHECK	= (byte)0xAB;
	
	private static final byte CMD_GET_CURRUNT_TIME = (byte)0xE1;
	private static final byte CMD_GET_SHUTDOWM_TIME = (byte)0xE2;
	private static final byte CMD_GET_START_TIME	= (byte)0xE3;
	private static final byte CMD_RESET_MACHINE	= (byte)0xE4;
	
	private static final byte CMD_LIGHT_ON	= (byte)0xC0;
	private static final byte COM_LICHT_OFF	= (byte)0xC1;
	private static final byte CMD_LIGHT_ON_STEADY  = (byte)0xC2;
	//private static final byte CMD_RTC_CHECK	= (byte)0xEB;
	private Context context;
	static{

		System.load("/system/lib/libencrypt.so");
	}
	
	public encryptService(Context ctx){
		context=ctx;
	
		Log.i("encryptService","Go to get encrypt Stub");
		if(OpenDevice()<0)
		{
			Log.i("encryptService","PIC_Init Open file faild!.....");
		}
		else
		{
			Log.i("encryptService","PIC_Init Open file succeed!");
		}

	}
	
	/*************************************
		int SetPICDateTime（String dateTime）
		功能：设置PIC的当前时间
		输入：日期时间： dateTime 20130608082255
		返回：0表示设置成功，-1表示失败
	*****************************************/

	/*************************************
	int GetPICStartUpTime(StringBuffer dateTime)
	功能：查询PIC的自动启动时间
	输出：dateTime 20130608082255
	返回：0表示查询成功，-1表示失败
	 *****************************************/
	public int GetPinData(StringBuffer pindata)
	{
		String str;
		Log.i("encryptService","GetPinReadPin");
		str = GetPinReadPin();
		if(str.equalsIgnoreCase("error"))
		{
			pindata.append("");
			return -1;
		}
		pindata.append(str);
		return 0;
	}
	
	/*************************************
	int GetPICShutDownTime (StringBuffer dateTime)
	功能：查询PIC的自动关机时间
	输出：dateTime 20130608082255
	返回：0表示查询成功，-1表示失败
	 *****************************************/
	public int GetVersionData(int DevType,StringBuffer versiondata)
	{
		String str;
		str = GetVersion(DevType);
		if(str.equalsIgnoreCase("error"))
		{
			versiondata.append("error");
			return -1;
		}
		versiondata.append(str);
		return 0;
	}

	public int GetPassWordData(StringBuffer password)
	{
		String str;
		str = GetEryPassWord();
		if(str.equalsIgnoreCase("error"))
		{
			password.append("error");
			return -1;
		}
		password.append(str);
		return 0;
	}

	/*************************************
	int RTCEncryptionCheck 
	功能：获取RTC加密值
	输出：
	返回：0表示查询成功，-1表示失败
	 *****************************************/
	//public int EncryptionData(String data,String encryptdata)
	public int EncryptionData(String data,StringBuffer encryptdata)
	{		
		String str = new String("");
		str = Encryption(data.getBytes(),data.length());
		if(str.equalsIgnoreCase("error"))
		{
			//encryptdata = "error"; 
			encryptdata.append("error");
			return -1;
		}
		Log.i("encryptService","EncryptionData str = "+str);
		//encryptdata = str;
		encryptdata.append(str);
		return 0;
	}
	
	//public int UnEncryptionData(String encryptdata,String data)
	public int UnEncryptionData(String encryptdata,StringBuffer data)
	{		
		String str = new String("");
		str = UnEncryption(encryptdata.getBytes(),encryptdata.length());
		if(str.equalsIgnoreCase("error"))
		{
			//data = "error";
			data.append("error");
			return -1;
		}
		//data = str;
		data.append(str);
		return 0;
	}
	
	public int GetCardNumberData(StringBuffer CardNumberData)
	{
		String str;
		str= GetCardNumber();
		if(str.equalsIgnoreCase("error"))
		{
			CardNumberData.append("error");
			return -1;
		}
		CardNumberData.append(str);
		return 0;
	}
	
	
	public int LoadWorkKeyData(String workkey)
	{
		//String str;
		int ret;
		char[] tmp = workkey.toCharArray() ;
		byte[] tmpbyte = workkey.getBytes(); 
		Log.i("###KMY###","tmpbyte[0] = "+tmpbyte[0]+" tmpbyte[1] = "+tmpbyte[1]+" workkey.length = "+workkey.length());
		Log.i("###KMY###","tmp.length ="+tmp.length+" tmp[1] = "+tmp[1]);
		ret= LoadWorkKey(workkey.getBytes(),workkey.length());
		//ret= TmpLoadWorkKey(tmp,tmp.length);
//		if(str.equalsIgnoreCase("error"))
//		{			
//			return -1;
//		}		
		return ret;
	}
	
	public int LoadMasterKeyData(String masterkey,int masterkeyid)
	{
		int ret;
		char[] tmp = masterkey.toCharArray();
		byte[] tmpbyte = masterkey.getBytes(); 
		Log.i("###KMY###","tmpbyte[0] = "+tmpbyte[0]+" tmpbyte[1] = "+tmpbyte[1]+" workkey.length = "+masterkey.length());
		Log.i("###KMY###","tmp.length ="+tmp.length+" tmp[1] = "+tmp[1]);
		ret= LoadMasterKey(masterkey.getBytes(),masterkey.length(),masterkeyid);		
		return ret;
	}
	
	public void Encrypt_rejectcardOut()
	{
		Log.i("###KMY###","Encrypt_rejectcardOut");
		RejectCardOut();
		return ;
	}
	
	public void Encrypt_close()
	{
		CloseDevice();
		return ;
	}	
	
//	public int Encrypt_LoadWorkKey(String key)
//	{
//		return LoadWorkKey(key.getBytes(),key.length());
//	}
	
	private native static String Encryption(byte[] data,int len);
	private native static String UnEncryption(byte[] Encryptdata,int len);
	private native static String  GetPinReadPin();
	private native static String  GetVersion(int DevType);
	private native static String  GetCardNumber();
	private native static int 	 OpenDevice();
	private native static void CloseDevice();
	private native static int LoadWorkKey(byte[] keydata,int length);
	private native static int TmpLoadWorkKey(char[] workkey,int len);
	private native static String  GetEryPassWord();
	private native static void RejectCardOut();
	private native static int LoadMasterKey(byte[] keydata,int length,int keyid);
}