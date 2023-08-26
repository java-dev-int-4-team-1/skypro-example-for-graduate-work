package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Collection<Comment> findByIdAndAdId(int id, int adId);
    Collection<Comment> findByAdId(int adId);
}
