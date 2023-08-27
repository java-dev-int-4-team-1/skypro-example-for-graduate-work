package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "comments")
public class Comment extends AbstractEntity {

    @JoinColumn(name = "ad_id")
    @ManyToOne
    Ad ad;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User author;

    long createdAt;

    String text;
}
