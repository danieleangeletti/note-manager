package com.manager.controller.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manager.model.LoginInput;
import com.manager.model.LoginOutput;
import com.manager.security.JwtProvider;

@RestController 
@RequestMapping("authentication")
public class Authentication {
	
	 @PostMapping
	 	// LoginInput è una classe che conterrà username e password, mentre LoginOutput è una classe che
	 	// ci viene ritornata che conterrà semplicemente il token generato.
	    public ResponseEntity<LoginOutput> login(@RequestBody LoginInput body) { 
	        // verifica se l'utente è registrato su db
	    	String user="";
	    	if(body.getPwd().equals("1234") && body.getUsername().equals("admin"))
	    		user = "jhonny";
	        
	        if (user.equals("")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	        }

			// Qui aggiungiamo le varie informazioni dell'utente, in questo caso soltanto la stringa user.
	        Map claimMap = new HashMap<>();
	        claimMap.put("user", user);

			// Qui creiamo un token con i parametri indicati: subject e payloadClaims
	        String jwt = JwtProvider.createJwt(user, claimMap);

			// START DEBUG: STAMPIAMO IL TOKEN GENERATO SUBITO PRIMA DI RESTITUIRLO
		 	System.out.println("Token generato, prima di restituirlo: " + jwt);
			// END DEBUG

			// Questo token lo aggiungiamo nella classe LoginOutput
	        LoginOutput tmp = new LoginOutput();
	        tmp.setToken(jwt);
	        return ResponseEntity.ok(tmp); 
	    } 
}

