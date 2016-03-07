package eeepay.androidmorefunctiondemo.phototwo;

import android.os.Environment;

import java.io.File;

/**
 * Created by xqf on 2016/1/19.
 */
public class FileUtils {

    public static String getFilePath(String filename){
        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDcardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdDir = Environment.getRootDirectory().getAbsolutePath();
        }
        //确定相片保存路径
        String targetDir = sdDir + "/" + "xqf_test";
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        return targetDir+"/"+filename;
    }
}
