package com.onlineexam.online_exam_backend.service;

import com.onlineexam.online_exam_backend.dto.StudentDashboardResponse;
import com.onlineexam.online_exam_backend.dto.StudentExamDTO;
import com.onlineexam.online_exam_backend.dto.StudentProfileDTO;
import com.onlineexam.online_exam_backend.entity.ExamAssignment;
import com.onlineexam.online_exam_backend.entity.Student;
import com.onlineexam.online_exam_backend.entity.Exam;
import com.onlineexam.online_exam_backend.repository.ExamAssignmentRepository;
import com.onlineexam.online_exam_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamAssignmentRepository examAssignmentRepository;

    public StudentDashboardResponse getDashboardData(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ExamAssignment> assignments = examAssignmentRepository.findByStudent(student);
        
        // Calculate statistics
        int totalAssigned = assignments.size();
        LocalDateTime now = LocalDateTime.now();
        
        int upcoming = (int) assignments.stream()
            .filter(a -> a.getExam().getStartTime().isAfter(now))
            .count();
            
        int completed = (int) assignments.stream()
            .filter(a -> a.getExam().getEndTime().isBefore(now))
            .count();
            
        int pending = totalAssigned - completed;

        // Get recent exams (last 5)
        List<StudentExamDTO> recentExams = assignments.stream()
            .sorted((a, b) -> b.getAssignedAt().compareTo(a.getAssignedAt()))
            .limit(5)
            .map(this::convertToStudentExamDTO)
            .collect(Collectors.toList());

        // Sample announcements (can be enhanced later)
        List<String> announcements = List.of(
            "New exam schedule has been updated",
            "Please check your upcoming exams",
            "System maintenance scheduled for this weekend"
        );

        return new StudentDashboardResponse(
            student.getFullName(),
            student.getDepartment(),
            student.getYear(),
            totalAssigned,
            upcoming,
            completed,
            pending,
            recentExams,
            announcements
        );
    }

    public List<StudentExamDTO> getAssignedExams(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ExamAssignment> assignments = examAssignmentRepository.findByStudent(student);
        return assignments.stream()
            .map(this::convertToStudentExamDTO)
            .collect(Collectors.toList());
    }

    public List<StudentExamDTO> getUpcomingExams(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        LocalDateTime now = LocalDateTime.now();
        List<ExamAssignment> assignments = examAssignmentRepository.findByStudent(student);
        
        return assignments.stream()
            .filter(a -> a.getExam().getStartTime().isAfter(now))
            .map(this::convertToStudentExamDTO)
            .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
            .collect(Collectors.toList());
    }

    public List<StudentExamDTO> getCompletedExams(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        LocalDateTime now = LocalDateTime.now();
        List<ExamAssignment> assignments = examAssignmentRepository.findByStudent(student);
        
        return assignments.stream()
            .filter(a -> a.getExam().getEndTime().isBefore(now))
            .map(this::convertToStudentExamDTO)
            .sorted((a, b) -> b.getEndTime().compareTo(a.getEndTime()))
            .collect(Collectors.toList());
    }

    public StudentExamDTO getExamDetails(Long examId, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamAssignment assignment = examAssignmentRepository.findByStudent(student)
            .stream()
            .filter(a -> a.getExam().getId().equals(examId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Exam not assigned to this student"));

        return convertToStudentExamDTO(assignment);
    }

    public String startExam(Long examId, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamAssignment assignment = examAssignmentRepository.findByStudent(student)
            .stream()
            .filter(a -> a.getExam().getId().equals(examId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Exam not assigned to this student"));

        Exam exam = assignment.getExam();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(exam.getStartTime())) {
            throw new RuntimeException("Exam has not started yet");
        }

        if (now.isAfter(exam.getEndTime())) {
            throw new RuntimeException("Exam has already ended");
        }

        // TODO: Create exam session logic here
        return "Exam started successfully. You can now proceed with the questions.";
    }

    public StudentProfileDTO getStudentProfile(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ExamAssignment> assignments = examAssignmentRepository.findByStudent(student);
        int totalAssigned = assignments.size();
        
        LocalDateTime now = LocalDateTime.now();
        int completed = (int) assignments.stream()
            .filter(a -> a.getExam().getEndTime().isBefore(now))
            .count();

        // TODO: Calculate average score from exam results
        double averageScore = 0.0; // Placeholder

        return new StudentProfileDTO(
            student.getUser().getRollNo(),
            student.getFullName(),
            student.getDepartment(),
            student.getYear(),
            totalAssigned,
            completed,
            averageScore
        );
    }

    private StudentExamDTO convertToStudentExamDTO(ExamAssignment assignment) {
        Exam exam = assignment.getExam();
        LocalDateTime now = LocalDateTime.now();
        
        String status;
        boolean canStart = false;
        
        if (now.isBefore(exam.getStartTime())) {
            status = "UPCOMING";
        } else if (now.isAfter(exam.getEndTime())) {
            status = "COMPLETED";
        } else {
            status = "ONGOING";
            canStart = true;
        }

        return new StudentExamDTO(
            exam.getId(),
            exam.getTitle(),
            exam.getSubject(),
            exam.getTeacher().getName(),
            exam.getTotalMarks(),
            exam.getDurationMinutes(),
            exam.getStartTime(),
            exam.getEndTime(),
            assignment.getAssignedAt(),
            status,
            canStart,
            exam.getQuestions().size()
        );
    }
}