package com.up.onetouch.bo;

import org.springframework.web.client.HttpServerErrorException;

import android.content.SharedPreferences;
import android.os.NetworkOnMainThreadException;
import com.up.onetouch.activity.AbstractActivity;
import com.up.onetouch.bean.LoginRequest;
import com.up.onetouch.bean.LoginResponse;
import com.up.onetouch.constants.Mensagens;

public class LoginBO extends AbstractBO {

	public LoginBO(AbstractActivity activity) {
		super(activity);
	}

	public LoginResponse doLogin(LoginRequest login) throws NetworkOnMainThreadException, HttpServerErrorException {
		
		LoginResponse resp = getActivity().getRestClient().login(login);
		
		if(resp.getMensagem() == Mensagens.LOGIN_OK){
			SharedPreferences.Editor editor = getActivity().getPrefs().edit();
			editor.putString("login", login.getLogin());
			editor.putString("senha", login.getSenha());
			if(!editor.commit()){
				resp.setMensagem(Mensagens.ERRO_PREFERENCES);
			}
		}
		
		return resp;
	}	
}