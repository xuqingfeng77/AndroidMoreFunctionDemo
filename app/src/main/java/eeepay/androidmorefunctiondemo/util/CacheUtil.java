package eeepay.androidmorefunctiondemo.util;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 把对象写到文件中
 * 2015-11-19
 * xqf
 *
 */
public class CacheUtil {
    private static File sdCardDir;
    private static File sdFile;
    /**
     *
     * @param cacheObj
     * @param cachename  缓存文件名 cache.out
     *  特别说明：混淆的时候就要注意不要混淆了泛型
     *     使用方法：readyToWriter(mStudent,"cache.out");
     *                   mStudent需要implements Serializable
     */
    public static void readyToWriter(Object cacheObj,String cachename)
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            sdCardDir = Environment.getExternalStorageDirectory();
            sdFile = new File(sdCardDir, cachename);//输出到根目录
            try
            {
                FileOutputStream fos = new FileOutputStream(sdFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(cacheObj);
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    /**
     *写在和app相关联的内部存储中；;data/data/packagename，私有
     */
    public static void readyToWriterInside(Context ctx,Object cacheObj,String cachename)
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            try
            {
                FileOutputStream fos = ctx.openFileOutput(cachename, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(cacheObj);
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
        }

    }
    /**
     *
     * @param packagename   Class.forName("eeepay.androidmorefunctiondemo.Student")
     * @param cachename  缓存文件名 cache.out
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * 特别说明：混淆的时候就要注意不要混淆了泛型
     * 使用方法：   Student student=(Student) readyToReader(Class.forName("eeepay.androidmorefunctiondemo.Student"),"cache.out");
     * Student需要implements  Serializable
     */
    public static <T extends Object> T readyToReader(Class<T> packagename,String cachename) throws InstantiationException,IllegalAccessException

    { Object  catchObj=null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        { sdCardDir = Environment.getExternalStorageDirectory();
            sdFile = new File(sdCardDir, cachename);//输出到根目录
            try
            {
                FileInputStream fis = new FileInputStream(sdFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                catchObj =  ois.readObject();
                fis.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return (T) catchObj;
    }

    /**
     * 写在和app相关联的内部存储中;data/data/packagename，私有
     * @param ctx
     * @param packagename
     * @param cachename
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Object> T readyToReaderInside(Context ctx,Class<T> packagename,String cachename) throws InstantiationException,IllegalAccessException

    { Object  catchObj=null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {  try {
            FileInputStream inputStream = ctx.openFileInput(cachename);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            catchObj =  ois.readObject();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        return (T) catchObj;
    }
    private void deleInsideFile(Context ctx){
      File file=  ctx.getFilesDir();

    }
}