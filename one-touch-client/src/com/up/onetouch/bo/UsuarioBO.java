package com.up.onetouch.bo;

import org.springframework.web.client.HttpServerErrorException;

import com.up.onetouch.activity.AbstractActivity;
import com.up.onetouch.bean.Usuario;
import com.up.onetouch.bean.UsuarioResponse;
import android.os.NetworkOnMainThreadException;

public class UsuarioBO extends AbstractBO {
	
	public UsuarioBO(AbstractActivity activity) {
		super(activity);
	}

	public UsuarioResponse doRegister(Usuario usr)  throws NetworkOnMainThreadException, HttpServerErrorException {
		UsuarioResponse resp = getActivity().getRestClient().persist(usr);
		return resp;
	}
}
