package com.gun0912.tedpermission.util;

import android.util.Log;


public class Dlog {

	static final String TAG = "tedpark";

	
	 /** Log Level Error **/
	public static  void e(String message) {
		Log.e(TAG, buildLogMsg(message));
	}
	 /** Log Level Warning **/
	public static  void w(String message) {
		Log.w(TAG, buildLogMsg(message));
	}
	 /** Log Level Information **/
	public static  void i(String message) {
		Log.i(TAG, buildLogMsg(message));
	}
	/** Log Level Debug **/
	public static  void d(String message) {
		Log.d(TAG, buildLogMsg(message));
	}
	/** Log Level Verbose **/
	public static  void v(String message){Log.v(TAG, buildLogMsg(message));
	}
	

	public static String buildLogMsg(String message) {

		StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

		StringBuilder sb = new StringBuilder();

		sb.append("[");
		sb.append(ste.getFileName().replace(".java", ""));
		sb.append("::");
		sb.append(ste.getMethodName());
		sb.append("]");
		sb.append(message);

		return sb.toString();

	}

}