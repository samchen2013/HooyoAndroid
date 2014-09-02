package com.hooyo.asd;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoadRecordActivity extends Activity {
	private static final String tag = "LoadRecordActivity";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 = 101;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE2 = 102;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE3 = 103;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE4 = 104;
	private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
	private static String picFileFullName="";
	private static String picName="";
	
	private String[] picPaths=new String[]{"","","",""};

	private String ID;
	private ImageView ImV_pic1;
	private ImageView ImV_pic2;
	private ImageView ImV_pic3;
	private ImageView ImV_pic4;
	private EditText  ed_sjdw;
	private EditText ed_js;
	private TextView tv_CarNo3;
	private ImageView bt_save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_record2);
		
		InitView();
		Bundle bundle=this.getIntent().getExtras();
		String carNo=bundle.getString("CARNO");
		ID=bundle.getString("ID");
		tv_CarNo3.setText(carNo);
		
		bt_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveAllPictrues();
			}
		});
		//单击事件
		ImV_pic1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(picPaths[0].equals("")){
					String msg=takePicture(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1);
					if(msg.equals("拍照失败，请确认已经插入SD卡！"))
						Toast.makeText(LoadRecordActivity.this, msg,
								Toast.LENGTH_SHORT).show();
					else
						picPaths[0]=msg;
				}else
				{
					openPictrue(picPaths[0]);
				}
			}
		});
		//长按事件
		ImV_pic1.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		ImV_pic2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveAllPictrues();
			}
		});
		
		ImV_pic3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveAllPictrues();
			}
		});
		
		ImV_pic4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveAllPictrues();
			}
		});
		
		
		
	}
	
	
	protected void openPictrue(String string) {
		// TODO Auto-generated method stub
		
	}


	protected void saveAllPictrues() {
		// TODO Auto-generated method stub
		
	}


	//拍照
    public String takePicture(int requestCode){
    	String state = Environment.getExternalStorageState();  
        if (state.equals(Environment.MEDIA_MOUNTED)) {  
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);  
            if (!outDir.exists()) {  
            	outDir.mkdirs();  
            }  
            String fileName=System.currentTimeMillis()+"";
//            picName=fileName;
            File outFile =  new File(outDir, fileName + ".jpg");  
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));  
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);  
            startActivityForResult(intent, requestCode);  //CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1
            return picFileFullName;
        } else{
//        	Log.e(tag, "请确认已经插入SD卡");
        	return "拍照失败，请确认已经插入SD卡！";
        }
    }
    
    @Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		super.onActivityResult(requestCode, resultCode, data);
 		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1) {//第一张图片
 			if (resultCode == RESULT_OK) {
 				Log.e(tag, "获取图片成功，path="+picFileFullName+"picpath0:"+picPaths[0]);
 				toast("获取图片成功，path="+picFileFullName);
 				setImageView(ImV_pic1,picPaths[0]);
 			} else if (resultCode == RESULT_CANCELED) {
 				// 用户取消了图像捕获
 			} else {
 				// 图像捕获失败，提示用户
 				Log.e(tag, "拍照失败");
 			}
 		} 
 	}
 	
 	private void setImageView(ImageView imageView,String realPath){
 		Bitmap bmp = BitmapFactory.decodeFile(realPath);
 		int degree = readPictureDegree(realPath);
 		if(degree <= 0){
 			imageView.setImageBitmap(bmp);
 		}else{
 			Log.e(tag, "rotate:"+degree);
 			//创建操作图片是用的matrix对象
 	 		Matrix matrix=new Matrix();
 	 		//旋转图片动作
 	 		matrix.postRotate(degree);
 	 		//创建新图片
 	 		Bitmap resizedBitmap=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
 	 		imageView.setImageBitmap(resizedBitmap);
 		}
 	}
 	
 	/**
     * This method is used to get real path of file from from uri<br/>
     * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-captured-from-camera
     * 
     * @param contentUri
     * @return String
     */
	public String getRealPathFromURI(Uri contentUri){
        try{
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this method, 
            // because the activity will do that for you at the appropriate time
            Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }catch (Exception e){
            return contentUri.getPath();
        }
	}
	
	/** 
     * 读取照片exif信息中的旋转角度<br/>
     * http://www.eoeandroid.com/thread-196978-1-1.html
     * 
     * @param path 照片路径 
     * @return角度 
     */  
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
    public void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private void InitView() {
		  ImV_pic1=(ImageView)findViewById(R.id.ImV_pic1);
		 ImV_pic2=(ImageView)findViewById(R.id.ImV_pic2);
		  ImV_pic3=(ImageView)findViewById(R.id.ImV_pic3);
		  ImV_pic4=(ImageView)findViewById(R.id.ImV_pic4);
		  ed_sjdw=(EditText)findViewById(R.id.ed_sjdw);
		 ed_js=(EditText)findViewById(R.id.ed_js);
		 tv_CarNo3=(TextView)findViewById(R.id.tv_CarNo3);
		bt_save=(ImageView)findViewById(R.id.bt_saveallpci);
	}
}
