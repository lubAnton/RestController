package ru.kata.spring.boot_security.demo.DTO;

import lombok.Data;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Objects;

@Data
public class UserDTO {
    private int id;

    private String username;
    private String surname;
    private int age;
    private String password;
    private String name;
    private List<Role> roles;

    public UserDTO() {
    }

    public UserDTO(int id, String username, String surname, int age, String password, String name, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.age = age;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
