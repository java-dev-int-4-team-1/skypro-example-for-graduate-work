package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private Role role;

    private String image;
    /** ToDo fix thess relations */
    /*@ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name="ad_pk")
    private Ad ad;
    */



}
