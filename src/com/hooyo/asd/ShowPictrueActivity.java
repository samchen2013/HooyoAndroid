package com.hooyo.asd;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ShowPictrueActivity extends Activity {

	private ImageView pic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_pictrue);
		
		Bundle bundle=this.getIntent().getExtras();
		String path=bundle.getString("PATH");
		pic=(ImageView)findViewById(R.id.myPic);
		setImageView(pic,path);
	}
	private void setImageView(ImageView imageView,String realPath){
 		Bitmap bmp = BitmapFactory.decodeFile(realPath);
 		int degree = readPictureDegree(realPath);
 		if(degree <= 0){
 			imageView.setImageBitmap(bmp);
 		}else{
 			Log.e("showpic", "rotate:"+degree);
 			//创建操作图片是用的matrix对象
 	 		Matrix matrix=new Matrix();
 	 		//旋转图片动作
 	 		matrix.postRotate(degree);
 	 		//创建新图片
 	 		Bitmap resizedBitmap=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
 	 		imageView.setImageBitmap(resizedBitmap);
 		}
 	}
	public static int readPictureDegree(String path) {  
        int degree  = 0;  
        try {  
                ExifInterface exifInterface = new ExifInterface(path);  
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
                switch (orientation) {  
                case ExifInterface.ORIENTATION_ROTATE_90:  
                        degree = 90;  
                        break;  
                case ExifInterface.ORIENTATION_ROTATE_180:  
                        degree = 180;  
                        break;  
                case ExifInterface.ORIENTATION_ROTATE_270:  
                        degree = 270;  
                        break;  
                }  
        } catch (IOException e) {  
                e.printStackTrace();  
        }  
        return degree;  
}  
}
