package com.nothing.itsmyproject.repository;


import com.nothing.itsmyproject.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

}
