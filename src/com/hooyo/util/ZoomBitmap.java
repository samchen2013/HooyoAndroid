package com.hooyo.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * @author sam
 *
 */
public class ZoomBitmap {

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
	
	
	public static String toLow1Mbase64String(String picPath) {
		String base64Str = "";
		Bitmap bitmap = BitmapFactory.decodeFile(picPath);
		float wight = bitmap.getWidth();
		float height = bitmap.getHeight();

		Bitmap upbitmap = ZoomBitmap.zoomImage(bitmap, 1280, 720);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		upbitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
		byte[] b = stream.toByteArray();
		// 将图片流以字符串形式存储下来
		base64Str = new String(Base64Coder.encode(b));
		return base64Str;

	}
}
