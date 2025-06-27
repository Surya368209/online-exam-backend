package com.onlineexam.online_exam_backend.repository;

import com.onlineexam.online_exam_backend.entity.ExamAssignment;
import com.onlineexam.online_exam_backend.entity.Student;
import com.onlineexam.online_exam_backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExamAssignmentRepository extends JpaRepository<ExamAssignment, Long> {

    // Find assignments for a particular exam
    List<ExamAssignment> findByExam(Exam exam);

    // Find assignments for a specific student
    List<ExamAssignment> findByStudent(Student student);

    // Optional: Check if a student is already assigned an exam
    boolean existsByExamAndStudent(Exam exam, Student student);



    List<ExamAssignment> findByExamId(Long examId);
    List<ExamAssignment> findByStudentId(UUID studentId);

    List<ExamAssignment> findByDepartment(String department); 
}
