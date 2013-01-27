package cz.muni.fi.pa165.vozovna.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.security.authentication.encoding.PasswordEncoder;


/**
 * Callback for setting the password for web-service clients
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public class ClientPasswordCallback implements CallbackHandler{
	
	private PasswordEncoder passwordEncoder = new org.springframework.security.authentication.encoding.ShaPasswordEncoder(256);
	
	@Override
	public void handle(Callback[] callbacks) throws IOException,
	UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		pc.setPassword(passwordEncoder.encodePassword("rest",""));     
	}
}