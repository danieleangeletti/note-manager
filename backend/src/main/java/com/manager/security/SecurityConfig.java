package com.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// @Configuration indica che la classe è una classe di configurazione Spring, quindi che definisce uno o più bean.
@Configuration
// @EnableWebSecurity abilita il supporto per la sicurezza delle applicazioni web, quindi permette di personalizzare
// la configurazione di sicurezza HTTP, definire le regole di accesso alle diverse parti dell'applicazione e gestire
// altre funzionalità di sicurezza come CSRF.
@EnableWebSecurity
// @EnableGlobal... permette di proteggere metodi specifici in base ai permessi, utilizzando annotazioni come
// @PreAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Queste sono il secret, il param e il prefisso del nostro token
    public static String secret;
    public static String param;
    public static String prefix;

    // Questo metodo semplicemente ci permette di leggere i parametri definiti in application.properties
    @Autowired
    public SecurityConfig(Environment env) {
        SecurityConfig.secret = env.getProperty("security.secret");
        SecurityConfig.param = env.getProperty("security.param");
        SecurityConfig.prefix = env.getProperty("security.prefix");

        // START DEBUG VERIFICA PARAMETRI application.properties
        System.out.println("Chiave segreta caricata: " + SecurityConfig.secret);
        System.out.println("Parametro di autorizzazione: " + SecurityConfig.param);
        System.out.println("Prefisso del token: " + SecurityConfig.prefix);
        // END DEBUG
    }

    // Qui configuriamo la security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disabilitiamo il csrf che è un particolare tipo di controllo hacker che viene utilizzato per quanto riguarda
        // le pagine HTML;
        http.csrf().disable();
        // Con addFilter stiamo aggiungendo un filtro (AuthorizationFilter) che dovrà autenticare qualsiasi richiesta:
        // tutte le richieste saranno intercettate dalla classe AuthorizationFilter
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(new AuthorizationFilter(authenticationManager())).authorizeRequests()
                .antMatchers("/authentication/**").permitAll()
                .anyRequest().authenticated();
    }
}
