package cz.muni.fi.pa165.vozovna.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class ClientPasswordCallback implements CallbackHandler{
	
	private PasswordEncoder passwordEncoder = new org.springframework.security.authentication.encoding.ShaPasswordEncoder(256);
	
	@Override
	public void handle(Callback[] callbacks) throws IOException,
	UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		pc.setPassword(passwordEncoder.encodePassword("rest",""));     
	}
}