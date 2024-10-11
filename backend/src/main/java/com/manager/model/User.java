package com.manager.model;

// javax.persistence serve a mappare le classi Java agli oggetti del database.
import javax.persistence.*;

// @Entity segnala che la classe Java è una entità e deve essere mappata a una tabella del database.
@Entity
@Table(name = "users")
public class User {

    // @Id specifica la chiave primaria
    @Id
    // strategy specifica la strategia per generare il valore della chiave primaria. GenerationType.IDENTITY
    // utilizza una colonna con incremento automatico (come AUTO_INCREMENT su MySQL).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;

    public User(){
        super();
    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
