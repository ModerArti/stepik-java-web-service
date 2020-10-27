package com.github.moderarti.models.accounts;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class UserProfile implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;
    @Column(name = "password")
    private String password;

    public UserProfile() {}

    public UserProfile(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserProfile(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
