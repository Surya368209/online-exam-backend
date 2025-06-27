package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    // For single student by roll no
    Student findByUserRollNo(String rollNo);

    // For multiple students by roll no
    List<Student> findByUserRollNoIn(List<String> rollNos);

List<Student> findByDepartmentAndYear(String department, String year);

    Optional<Student> findByUserId(UUID userId);
}