package eeepay.androidmorefunctiondemo.photo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

public class Bimp {
	public static int max = 0;
	
	public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表
	public static ArrayList<ImageItem> temp2SelectBitmap = new ArrayList<ImageItem>();   //存储被删除的
	public static Bitmap revitionImageSize(String path) throws IOException {

		Bitmap bi=decodeFile(path,60);
		return bi;
	}
	/**
	 * 获取小图片，减少内存消耗
	 *
	 * @param path
	 *            图片路径
	 * @param picsize
	 *            宽的大小 bitmap大小
	 * @return
	 */
	public  static Bitmap decodeFile(String path, int picsize) {
		if (TextUtils.isEmpty(path)) {

			return null;
		}
		try {
			// decode image size
			File f = new File(path);
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = picsize;// 如果是 传给服务器的，就大一点
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp > height_tmp) {
					width_tmp = height_tmp;
				}
				if (width_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inJustDecodeBounds = false;
			o2.inSampleSize = scale;
			o2.inInputShareable = true;
			o2.inPurgeable = true;
			Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f),
					null, o2);

			return bm;
		} catch (FileNotFoundException e) {
		}
		return null;
	}
}
