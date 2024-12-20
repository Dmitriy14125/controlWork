package org.example;

import java.util.Date;
import java.util.regex.Pattern;

public class Person {
    private String name;
    private String birthday;
    private String email;

    private void validateName() {
        if (!Pattern.matches("[a-zA-Z]+", this.name)) {
            throw new IllegalArgumentException("Invalid name");
        }
    }

    private void validateEmail() {
        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", this.email)) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    private void validateBirthday() {
        if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", this.birthday)) {
            throw new IllegalArgumentException("Invalid birthday");
        }
    }

    public Person(String name, String birthday, String email) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.validateName();
        this.validateBirthday();
        this.validateEmail();
    }
    public Person() {
        this("","","");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
