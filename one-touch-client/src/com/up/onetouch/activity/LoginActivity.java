package com.up.onetouch.activity;

import android.content.SharedPreferences;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.up.onetouch.R;
import com.up.onetouch.bean.LoginRequest;
import com.up.onetouch.bean.LoginResponse;
import com.up.onetouch.bo.LoginBO;
import com.up.onetouch.constants.Mensagens;
import com.up.onetouch.rest.RestClient;

@EActivity(R.layout.login)
public class LoginActivity extends AbstractActivity {

	@ViewById(R.id.txtLogin)
	EditText txtLogin;
	
	@ViewById(R.id.txtSenha)
	EditText txtSenha;
	
	@Click(R.id.btLogin)
	void onClickLogin() {
		
		LoginRequest req = new LoginRequest();
		req.setLogin(txtLogin.getText().toString());
		req.setSenha(txtSenha.getText().toString());
		
		String msg = "";
		
		try{
			Log.i(CONNECTIVITY_SERVICE, "Conectando no servidor:" + getResources().getString(R.string.server_url));
		
			LoginBO bo = new LoginBO(this);			
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
	}
	
	@Click(R.id.btRegister)
	void onClickRegister(){
		openActivity(RegisterActivity_.class);
	}
}