// QUESTO CODICE SERVE A PERMETTERE L'ACCESSO A TUTTE LE RICHIESTE, SENZA ALCUNA AUTENTICAZIONE

/*

package com.manager;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration indica che questa classe è una classe di configurazione di Spring. In altre parole, Spring gestirà questa classe come parte del contesto dell'app.
@Configuration
// @EnableWebSecurity assicura che la tua applicazione consideri le configurazioni di sicurezza definite in questa classe.
@EnableWebSecurity
public class SecurityConfigNoAuth extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // Inizia la configurazione per autorizzare le richieste HTTP;
                .anyRequest().permitAll() // Permetti accesso a tutte le richieste senza alcuna autenticazione;
                .and()
                .csrf().disable(); // Disabilita CSRF per testing (usa con cautela in produzione)
    }
}

*/


