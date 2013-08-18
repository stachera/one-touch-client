package com.up.onetouch.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class UtilsActivity {
	
	public static void showMsg(Activity currentActivity, String message){		
		Toast.makeText(currentActivity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	public static void openActivity(Activity currentActivity, Class<?> openActivity){
		Intent intent = new Intent(currentActivity.getApplicationContext(), openActivity);
		currentActivity.startActivity(intent);		
	}
	
	public static void openActivity(Activity currentActivity, Class<?> openActivity, int flag){
		Intent intent = new Intent(currentActivity.getApplicationContext(), openActivity);
		intent.setFlags(flag);
		currentActivity.startActivity(intent);		
	}
}
