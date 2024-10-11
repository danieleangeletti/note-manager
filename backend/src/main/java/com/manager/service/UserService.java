package com.manager.service;

import com.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

// I servizi in Spring sono componenti che contengono la logica di business dell'applicazione.
@Service
public class UserService {

    // @Autowired viene utilizzata per iniettare le dipendenze necessarie all'interno della classe.
    @Autowired
    // JdbcTemplate è una classe fornita da Spring che semplifica l'interazione con il database.
    private JdbcTemplate jdbcTemplate;

    // Metodo per registrare un nuovo utente
    public User registerUser(User user) {
        // Controlliamo se l'email è già utilizzata

        // emailCheckQuery è la query che restituisce il numero di righe della tabella users dove il campo email
        // corrisponde a un valore specificato
        String emailCheckQuery = "SELECT COUNT(*) FROM users WHERE email = ?";

        // Il metodo queryForObject esegue la query: il primo parametro è la query, il secondo il tipo di oggetto
        // che ti aspetti come risultato, il terzo è il valore da sostituire al ?.

        // Perché usiamo Integer e non int? Perché int è un primitivo e non può essere null. Se non viene
        // inizializzato assumerà automaticamente il valore 0. Integer è un tipo di oggetto (wrapper class) che
        // incapsula un valore int, e può essere null. Questo è utile quando non hai un valore valido (tipo se
        // non ci sono risultati nella query)
        Integer countEmail = jdbcTemplate.queryForObject(emailCheckQuery, Integer.class, user.getEmail());
        if (countEmail != null && countEmail > 0) {
            throw new RuntimeException("Email already exists");
        }

        // Facciamo la stessa cosa per controllare se lo username è già utilizzato
        String usernameCheckQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer countUsername = jdbcTemplate.queryForObject(usernameCheckQuery, Integer.class, user.getUsername());
        if (countUsername != null && countUsername > 0) {
            throw new RuntimeException("Username already exists");
        }

        // Query per inserire un nuovo utente
        String insertQuery = "INSERT INTO users (name, username, email, password) VALUES (?, ?, ?, ?)";

        // Il metodo update di JdbcTemplate viene utilizzato per fare operazioni di aggiornamento sul database:
        // i parametri di base sono una query e una lista di argomenti.
        jdbcTemplate.update(insertQuery, user.getName(), user.getUsername(), user.getEmail(), user.getPassword());

        return user;
    }
}
