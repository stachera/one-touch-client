package com.up.onetouch.activity;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.up.onetouch.R;
import com.up.onetouch.bean.LoginRequest;
import com.up.onetouch.bean.LoginResponse;
import com.up.onetouch.bo.LoginBO;
import com.up.onetouch.constants.Mensagens;

@EActivity(R.layout.main)
public class MainActivity extends AbstractActivity {

	@AfterViews
	void afterViews() {
	    super.afterViews();
	    
	    if(getPrefs().contains("login")){
	    	String msg = "";

	    	try{
				LoginBO bo = new LoginBO(this);		
				LoginRequest req = new LoginRequest();
				req.setLogin(getPrefs().getString("login", ""));
				req.setSenha(getPrefs().getString("senha", ""));
				LoginResponse resp = bo.doLogin(req);
				
				if(resp.getMensagem() == Mensagens.LOGIN_OK){
					msg = Mensagens.LOGIN_OK.mensagem;
					openActivityClearHistory(PanelActivity_.class);
				} else {
					msg = resp.getMensagem().mensagem;
				}
			}catch(NetworkOnMainThreadException e){
				msg = Mensagens.ERRO_REDE.mensagem;
				Log.e(CONNECTIVITY_SERVICE, msg, e);
			} finally {
				showMsg(msg);
			}	    	
	    } else {
	    	showMsg("Efetue o login");
	    	openActivity(LoginActivity_.class);
	    }
	}
}