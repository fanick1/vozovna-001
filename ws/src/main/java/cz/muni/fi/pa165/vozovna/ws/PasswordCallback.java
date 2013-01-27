package cz.muni.fi.pa165.vozovna.ws;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.apache.ws.security.WSPasswordCallback;
public class PasswordCallback implements CallbackHandler {
	
	private static final Logger LGR = LoggerFactory.getLogger(PasswordCallback.class);
	
	@Autowired 
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		if(userDetailsService == null){
			throw new IllegalStateException("UserDetailsService was not set.");
		}
		
		if(callbacks == null){
			throw new IllegalArgumentException("Callbacks can't be null.");
		}
			
		 for (int i = 0; i < callbacks.length; i++) {

			 WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
	
			 if (pc.getUsage() == WSPasswordCallback.USERNAME_TOKEN) {
				 UserDetails ud = userDetailsService.loadUserByUsername(pc.getIdentifier());
				 if(ud != null){
					 LGR.warn("Prihlasovaci jmeno a heslo {} {} ",pc.getIdentifier(), pc.getPassword());
					 
					 pc.setPassword(ud.getPassword());
					 LGR.warn("Heslo v databazi {} ",ud.getPassword());
				 }
			 }
		 }
		 LGR.warn("PasswordCallback ended.");
	}		
}
