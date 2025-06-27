package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRollNoIgnoreCase(String rollNo); // This is the required method
}
