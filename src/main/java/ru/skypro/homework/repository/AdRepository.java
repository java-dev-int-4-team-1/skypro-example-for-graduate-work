package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.Collection;


public interface AdRepository extends JpaRepository <Ad,Integer> {

    Collection<Ad> findByAuthor(User currentUser);
}
