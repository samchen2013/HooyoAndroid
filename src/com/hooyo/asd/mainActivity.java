/**
 * 
 */
package com.hooyo.asd;


 
import com.hooyo.util.WebServiceHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

/**
 * @author Sam
 *
 */
public class mainActivity extends Activity {
	private TextView msg=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mian_text);
		msg=(TextView)findViewById(R.id.txtMsg);
		String dd=WebServiceHelper.getLogin("aaa", "bbb");
		msg.setText(dd);
	}

	/**
	 * 
	 */
	public mainActivity() {
		// TODO Auto-generated constructor stub
	}

}
