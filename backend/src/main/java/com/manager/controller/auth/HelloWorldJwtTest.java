package com.manager.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// @Controller indica che la classe è un controller nel contesto di un'applicazione Spring MVC. I controller gestiscono
// le richieste HTTP e restituiscono le risposte appropriate. I metodi possono restituire una vista e devono usare
// @ResponseBody per restituire i dati. È diverso da @RestController, annotazione che combina @Controller e @ResponseBody
// per creare un controller RESTful, i cui metodi restituiscono quindi sempre dati JSON, XML, ...
@Controller
@RequestMapping("/helloworldjwttest")
public class HelloWorldJwtTest {

    @GetMapping("/helloworld")
    // @ResponseBody indica che il valore restituito dal metodo deve essere inviato direttamente come risposta HTTP al client
    @ResponseBody
    public String helloWorld() {
        return "Hello, World!";
    }
}
