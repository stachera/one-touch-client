package com.up.onetouch.activity;

import android.content.SharedPreferences.Editor;
import android.os.StrictMode;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.up.onetouch.R;

@EActivity(R.layout.panel)
public class PanelActivity extends AbstractActivity {
	@Click(R.id.btClearPreferences)
	void onClickClearPreferences(){
		Editor editor = getPrefs().edit();
		editor.clear();
		editor.commit();		
		finish();
		
		openActivityClearHistory(MainActivity_.class);
	}
	
	@Click(R.id.btOpenProvador)
	void onClickOpenProvador(){
		openActivity(ProvadorActivity_.class);
	}
	
	@Click(R.id.btPgto)
	void onClickPgto(){
		openActivity(PagamentoActivity.class);
	}
}