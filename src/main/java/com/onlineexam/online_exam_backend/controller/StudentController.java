package com.onlineexam.online_exam_backend.controller;

import com.onlineexam.online_exam_backend.dto.StudentDashboardResponse;
import com.onlineexam.online_exam_backend.dto.StudentExamDTO;
import com.onlineexam.online_exam_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/dashboard")
    public ResponseEntity<StudentDashboardResponse> getDashboard(Principal principal) {
        String rollNo = principal.getName(); // Extracted from JWT
        StudentDashboardResponse response = studentService.getDashboardData(rollNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exams/assigned")
    public ResponseEntity<List<StudentExamDTO>> getAssignedExams(Principal principal) {
        String rollNo = principal.getName();
        List<StudentExamDTO> assignedExams = studentService.getAssignedExams(rollNo);
        return ResponseEntity.ok(assignedExams);
    }

    @GetMapping("/exams/upcoming")
    public ResponseEntity<List<StudentExamDTO>> getUpcomingExams(Principal principal) {
        String rollNo = principal.getName();
        List<StudentExamDTO> upcomingExams = studentService.getUpcomingExams(rollNo);
        return ResponseEntity.ok(upcomingExams);
    }

    @GetMapping("/exams/completed")
    public ResponseEntity<List<StudentExamDTO>> getCompletedExams(Principal principal) {
        String rollNo = principal.getName();
        List<StudentExamDTO> completedExams = studentService.getCompletedExams(rollNo);
        return ResponseEntity.ok(completedExams);
    }

    @GetMapping("/exams/{examId}/details")
    public ResponseEntity<StudentExamDTO> getExamDetails(@PathVariable Long examId, Principal principal) {
        String rollNo = principal.getName();
        StudentExamDTO examDetails = studentService.getExamDetails(examId, rollNo);
        return ResponseEntity.ok(examDetails);
    }

    @PostMapping("/exams/{examId}/start")
    public ResponseEntity<String> startExam(@PathVariable Long examId, Principal principal) {
        String rollNo = principal.getName();
        String result = studentService.startExam(examId, rollNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getStudentProfile(Principal principal) {
        String rollNo = principal.getName();
        return ResponseEntity.ok(studentService.getStudentProfile(rollNo));
    }
}