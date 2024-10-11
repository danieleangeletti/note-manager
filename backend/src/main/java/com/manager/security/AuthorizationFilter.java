package com.manager.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

// Siccome AuthorizationFilter è un filtro, deve estendere la base, cioè BasicAuthenticationFilter
public class AuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * Instantiates a new Authorization filter.
     *
     * @param authenticationManager the authentication manager
     */
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // Quello che ci interessa è il metodo doFilterInternal, metodo che verrà chiamato a ogni richiesta. Ogni richiesta
    // sarà bloccata da questo filtro.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Qui prendiamo l'header da SecurityConfig, quindi Authorization
    	String header = request.getHeader(SecurityConfig.param);

        // START DEBUG: VERIFICHIAMO CHE L'HEADER CONTENENTE IL TOKEN SIA PRESENTE
        System.out.println("Header Authorization ricevuto: " + header);
        // END DEBUG

        // Se header != null e soprattutto parte con SecurityConfig.prefix, cioè Bearer, vuol dire che stiamo prendendo
        // la parte l'autenticazione tramite token.
        if (header != null && header.startsWith(SecurityConfig.prefix)) {

            // START DEBUG: STAMPIAMO IL TOKEN ESTRATTO PRIMA DELLA VERIFICA
            String token = header.replace(SecurityConfig.prefix, "").trim();
            System.out.println("Token estratto prima della verifica: " + token);
            // END DEBUG

            try {

                // Qui dentro dobbiamo fare due operazioni:
                // prima decodificare il token, quindi verificare se è corretto. Per quanto riguarda la classe DecodedJWT
                // è una classe che fa parte di una dipendenza Java che ci fornisce una serie di metodi di utilità per
                // poter decodificare e codificare il nostro token;
                DecodedJWT decoded = JwtProvider.verifyJwt(header.replace(SecurityConfig.prefix, "").trim());

                // START DEBUG: VEDIAMO I REPLACE
                System.out.println("Token con replace sbagliato: " + header.replace(SecurityConfig.prefix, ""));
                System.out.println("Token con replace corretto: " + header.replace(SecurityConfig.prefix, "").trim());
                // END DEBUG

                // START DEBUG: STAMPIAMO IL SUBJECT DEL TOKEN DECODIFICATO
                System.out.println("Subject del token decodificato: " + decoded.getSubject());
                // END DEBUG

                // una volta che è stato decodificato dobbiamo aggiungere l'informazione del nostro token
                // decodificato. Senza l'aggiunta di questa informazione, alla fine dei vari filtri, l'applicazione
                // ci darebbe errore di autenticazione.
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(decoded.getSubject(), null, Collections.emptyList())
                );
            } catch (Exception e) {

                // START DEBUG: STAMPIAMO EVENTUALI ERRORI DI DECODIFICA
                System.out.println("Errore durante la decodifica del token: " + e.getMessage());
                // END DEBUG

            }

        }
        chain.doFilter(request, response);
    }
}
