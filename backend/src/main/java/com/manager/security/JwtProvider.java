package com.manager.security;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtProvider {

	  public static String createJwt(String subject,  Map<String, Object>  payloadClaims) {
		  Calendar cal = Calendar.getInstance();
		  // Creiamo una data con l'ora attuale
		  Date now = cal.getTime();
		  // Creiamo una data con l'ora aumentata di un'ora
		  cal.set(Calendar.HOUR_OF_DAY,cal.get(Calendar.HOUR_OF_DAY)+1);
		  Date nowMoreHour = cal.getTime();

		  // START DEBUG: STAMPIAMO LA CHIAVE UTILIZZATA PER FIRMARE IL TOKEN
		  System.out.println("Chiave segreta utilizzata per firmare il token: " + SecurityConfig.secret);
		  // END DEBUG

		  // START DEBUG: STAMPIAMO IL SUBJECT E I CLAIMS DEL TOKEN
		  System.out.println("Subject del token: " + subject);
		  System.out.println("Claims del token: " + payloadClaims);
		  // END DEBUG

		  // Qui creiamo il token
		  JWTCreator.Builder builder =  JWT.create()
				  	// il Subject è quello che abbiamo indicato
	                .withSubject(subject)
				  	// l'Issuer è il nome della nostra applicazione
	                .withIssuer("Demo JWT")
				  	// qui gli diciamo che il token deve partire ora
	                .withIssuedAt(now)
				  	// qui gli diciamo che il token deve scadere tra un'ora
	                .withExpiresAt(nowMoreHour);

	        if (payloadClaims != null && !payloadClaims.isEmpty()) {

				// Qui aggiungiamo le varie informazioni nel nostro builder
	        	for (Map.Entry<String, Object> entry : payloadClaims.entrySet()) {
	                builder.withClaim(entry.getKey(), entry.getValue().toString());
	            }
	        } else {
	            System.out.println("You are building a JWT without header claims!");
	        }

		    // START DEBUG: STAMPIAMO LA CHIAVE SEGRETA PRIMA DI FIRMARE IL TOKEN
		    System.out.println("Chiave segreta utilizzata per firmare il token: " + SecurityConfig.secret);
			// END DEBUG

			// Il builder è pronto: per poterlo creare è sufficiente firmarlo con la parola segreta, e lui ci ritornerà
		  	// in automatico il token pronto. In questo token avremo una serie di informazioni relativa al nostro utente
		  	// (possiamo aggiungere tutto quello che vogliamo, nome, cognome, ...)
	        return builder.sign(Algorithm.HMAC256(SecurityConfig.secret));
	    }

	// Questo metodo prende in ingresso un token, lo decodifica tramite l'algoritmo HMAC256 (aggiungendo la nostra parola
	// segreta, ciao), e ci ritorna una classe che ci dice se il token è stato decodificato o meno.
    public static DecodedJWT verifyJwt(String jwt) {

		// START DEBUG: COSA ACCADE DURANTE IL PROCESSO DI VERIFICA?
		System.out.println("Chiave segreta utilizzata per verificare il token: " + SecurityConfig.secret);
		// END DEBUG

		try {
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SecurityConfig.secret)).build().verify(jwt);

			// START DEBUG: STAMPIAMO IL RISULTATO DELLA VERIFICA
			System.out.println("Token verificato con successo. Subject: " + decodedJWT.getSubject());
			// END DEBUG

			return decodedJWT;
		} catch (Exception e) {

			// START DEBUG: STAMPIAMO EVENTUALI ERRORI DURANTE LA VERIFICA
			System.out.println("Errore nella verifica del token: " + e.getMessage());
			// END DEBUG

			throw e;
		}

    }

    private JwtProvider() {}
}
