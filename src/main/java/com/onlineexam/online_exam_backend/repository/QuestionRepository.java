package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.Question;
import com.onlineexam.online_exam_backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExam(Exam exam);
}