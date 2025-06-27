package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // This finds Teacher by the rollNo field inside the related User entity
    Teacher findByUserRollNo(String rollNo);
}