package com.hooyo.util;

public class StringUtil {
	public static String SetStringWidth(String myStr, int width)
	{
		String out=myStr;
		if(myStr.length()>width)
		{
			out=myStr.substring(0, width);
		}
		if(myStr.length()<width)
		{
			
			for(int i=0;i<width-myStr.length();i++)
				 out+=" ";
		}
		return out;
	}
	
	

}
