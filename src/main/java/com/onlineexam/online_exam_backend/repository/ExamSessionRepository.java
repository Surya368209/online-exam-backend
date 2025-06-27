package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.ExamSession;
import com.onlineexam.online_exam_backend.entity.Student;
import com.onlineexam.online_exam_backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
    
    // Find active session for a student and exam
    Optional<ExamSession> findByStudentAndExamAndIsSubmittedFalse(Student student, Exam exam);
    
    // Find all sessions for a student
    List<ExamSession> findByStudent(Student student);
    
    // Find all sessions for an exam
    List<ExamSession> findByExam(Exam exam);
    
    // Find completed sessions for a student
    List<ExamSession> findByStudentAndIsSubmittedTrue(Student student);
    
    // Check if student has already started/completed an exam
    boolean existsByStudentAndExam(Student student, Exam exam);
}