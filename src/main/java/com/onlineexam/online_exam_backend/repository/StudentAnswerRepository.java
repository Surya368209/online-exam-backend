package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.StudentAnswer;
import com.onlineexam.online_exam_backend.entity.ExamSession;
import com.onlineexam.online_exam_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    
    // Find all answers for an exam session
    List<StudentAnswer> findByExamSession(ExamSession examSession);
    
    // Find specific answer for a question in a session
    Optional<StudentAnswer> findByExamSessionAndQuestion(ExamSession examSession, Question question);
    
    // Count correct answers for a session
    long countByExamSessionAndIsCorrectTrue(ExamSession examSession);
    
    // Count total answered questions for a session
    long countByExamSessionAndSelectedAnswerIsNotNull(ExamSession examSession);
}