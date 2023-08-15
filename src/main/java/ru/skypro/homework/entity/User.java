package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private Role role;

    private String image;

   // @OneToMany
    @JoinColumn(name="comment_id")
    private Comment comment;

  //  @OneToMany
    @JoinColumn(name="ad_pk")
    private Ad ad;



}
