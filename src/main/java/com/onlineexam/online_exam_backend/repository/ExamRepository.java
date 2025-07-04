package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.Exam;
import com.onlineexam.online_exam_backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByTeacher(Teacher teacher);
}