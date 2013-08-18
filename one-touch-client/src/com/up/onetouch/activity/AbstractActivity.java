package com.up.onetouch.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.up.onetouch.R;
import com.up.onetouch.rest.RestClient;
import com.up.onetouch.utils.UtilsActivity;

@EActivity
public abstract class AbstractActivity extends SherlockActivity {
	
	public static final String PREFS_NAME = "OneTouch";
	
	@RestService
	RestClient restClient;
	
	@AfterViews
	void afterViews() {
		getRestClient().setRootUrl(getResources().getString(R.string.server_url));
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	public SharedPreferences getPrefs(){
		return getSharedPreferences(PREFS_NAME, 0);
	}

	void showMsg(String message){
		UtilsActivity.showMsg(this, message);
	}
	
	void openActivity(Class<?> openActivity){
		UtilsActivity.openActivity(this, openActivity);
	}
	
	void openActivityNoHistory(Class<?> openActivity){
		UtilsActivity.openActivity(this, openActivity, Intent.FLAG_ACTIVITY_NO_HISTORY);
	}
	
	void openActivityClearHistory(Class<?> openActivity){
		UtilsActivity.openActivity(this, openActivity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}

	public RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}
}
