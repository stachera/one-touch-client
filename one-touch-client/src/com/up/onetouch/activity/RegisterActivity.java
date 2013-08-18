package com.up.onetouch.activity;

import org.springframework.web.client.HttpServerErrorException;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.up.onetouch.R;
import com.up.onetouch.bean.Usuario;
import com.up.onetouch.bean.UsuarioResponse;
import com.up.onetouch.bo.UsuarioBO;
import com.up.onetouch.constants.Mensagens;
import com.up.onetouch.rest.RestClient;

import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

@EActivity(R.layout.register)
public class RegisterActivity extends AbstractActivity {

	@ViewById(R.id.txtLogin)
	EditText txtLogin;
	
	@ViewById(R.id.txtSenha)
	EditText txtSenha;
	
	@ViewById(R.id.txtConfirmarSenha)
	EditText txtConfirmarSenha;
	
	@Click(R.id.btRegisterOK)
	void onClickOK(){
		
		String msg = "";
		
		String login = txtLogin.getText().toString();
		String senha = txtSenha.getText().toString();
		String confSenha = txtConfirmarSenha.getText().toString();
		
		if(!login.isEmpty() && !senha.isEmpty() && !confSenha.isEmpty() && senha.equals(confSenha)){
			Usuario u = new Usuario();
			u.setLogin(login);
			u.setSenha(senha);
			
			try{
				UsuarioResponse resp = new UsuarioBO(this).doRegister(u);
				
				msg = resp.getMensagem().mensagem;
				
				if(resp.getMensagem() == Mensagens.PERSISTE_OK){
					openActivity(MainActivity_.class);
				}
			}catch(HttpServerErrorException e){
				msg = e.getMessage();
				Log.e(CONNECTIVITY_SERVICE, msg, e);
			}catch(NetworkOnMainThreadException e){
				msg = Mensagens.ERRO_REDE.mensagem;
				Log.e(CONNECTIVITY_SERVICE, msg, e);
			} finally {
				showMsg(msg);
			}
		} else {
			showMsg(Mensagens.WARN_DADOS_INCORRETOS.mensagem);
		}
	}
	
	@Click(R.id.btRegisterCancel)
	void onClickCancel(){
		super.onBackPressed();
	}
}
