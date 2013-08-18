
package com.up.onetouch.rest;

import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.up.onetouch.bean.LoginRequest;
import com.up.onetouch.bean.LoginResponse;
import com.up.onetouch.bean.Usuario;
import com.up.onetouch.bean.UsuarioResponse;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

@Rest(converters = {MappingJacksonHttpMessageConverter.class})
public interface RestClient {

    @Post("/login/validate")
    public LoginResponse login(LoginRequest request);
    
    @Post("/user/persist")
    public UsuarioResponse persist(Usuario usuario);

    void setRootUrl(String rootUrl);
}
