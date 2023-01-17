package com.kodilla.prices.external.prices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.prices.domain.offer.User;

public class UserDto {

    public UserDto(){
    }

    public UserDto(String id, String name, String lastName, String mail, String login, String password){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.login = login;
        this.password = password;
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("mail")
    private String mail;
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;

    public static UserDto fromDomain(User user){
        return new UserDto(
                user.id(),
                user.name(),
                user.lastName(),
                user.mail(),
                user.login(),
                user.password()
        );
    }

    public User toDomain(){
        return new User(
                id,
                name,
                lastName,
                mail,
                login,
                password
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
