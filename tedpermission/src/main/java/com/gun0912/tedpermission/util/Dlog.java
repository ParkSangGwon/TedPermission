package com.gun0912.tedpermission.util;

import android.util.Log;
import com.gun0912.tedpermission.BuildConfig;

public class Dlog {

	static final String TAG = "tedpark";

  /** Log Level Error **/
  public static void e(String message) {
    if (BuildConfig.DEBUG) Log.e(TAG, buildLogMsg(message));
  }

  /** Log Level Warning **/
  public static void w(String message) {
    if (BuildConfig.DEBUG) Log.w(TAG, buildLogMsg(message));
  }

  /** Log Level Information **/
  public static void i(String message) {
    if (BuildConfig.DEBUG) Log.i(TAG, buildLogMsg(message));
  }

  /** Log Level Debug **/
  public static void d(String message) {
    if (BuildConfig.DEBUG) Log.d(TAG, buildLogMsg(message));
  }

  /** Log Level Verbose **/
  public static void v(String message) {
    if (BuildConfig.DEBUG) Log.v(TAG, buildLogMsg(message));
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