package com.manager.controller.auth;

import com.manager.model.User;
import com.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// con web.bind.annotation.* importiamo tutte le annotazioni di questa libreria, annotazioni fondamentali per
// costruire applicazioni web e servizi RESTful. Stiamo parlando di:
// @RestController: indica che la classe è un controller web e che restituisce direttamente i dati (JSON) in risposta
// alle richieste HTTP;

// @RequestMapping: utilizzata per mappare le richieste HTTP a metodi specifici nella classe del controller;
// @GetMapping, @PostMapping, @PutMapping, @DeleteMapping sono varianti di @RequestMapping che semplificano la
// mappatura delle richieste specifiche per i metodi HTTP;

// @PathVariable: permette di estrarre variabili dall'URL della richiesta. Tipo se hai /users/{id}, puoi utilizzare
// @PathVariable per ottenere il valore di id;

// @RequestParam: utilizzata per estrarre i parametri della query dalla richiesta HTTP. Tipo in un URL come
// /search?query=java, puoi usare @RequestParam("query") per ottenere il valore di java;

// @RequestBody: permette di ricevere il corpo della richiesta HTTP, tipicamente in formato JSON, e di convertirlo
// in un oggetto Java.
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint per la registrazione di un nuovo utente
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);

            // ResponseEntity è una classe fornita da Spring he rappresenta l'intera risposta HTTP. Include non solo
            // il corpo della risposta (in questo caso l'oggetto User), ma anche altri dettagli importanti come gli
            // header e il codice di stato HTTP.

            // HttpStatus è un enum che fornisce codici di stato standard HTTP, aiutando a comunicare il risultato
            // delle richieste tra il server e il client.
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
