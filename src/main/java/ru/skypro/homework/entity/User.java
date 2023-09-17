package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends ImageEntity{

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Realm getRealm() {
        return Realm.USER;
    }
}
