package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int pk;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User author;

    private String text;

    private long createdAt;
}
