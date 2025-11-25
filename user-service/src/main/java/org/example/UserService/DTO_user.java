package org.example.UserService;

import jakarta.persistence.Column;

public class DTO_user {
    String name;
    String surname;
    String patronymic;
    String password;

    public String getFullName(){
        if (patronymic != null) {
            return name + " " + surname + " " + patronymic;
        }else {
            return name + " " + surname;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
