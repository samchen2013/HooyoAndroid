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
		// ��ȡ���ͼƬ�Ŀ�͸�
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ������������
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ����ͼƬ����
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
		// ��ͼƬ�����ַ�����ʽ�洢����
		base64Str = new String(Base64Coder.encode(b));
		return base64Str;

	}
}
