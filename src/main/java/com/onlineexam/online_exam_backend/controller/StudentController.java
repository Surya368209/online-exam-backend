package com.onlineexam.online_exam_backend.controller;

import com.onlineexam.online_exam_backend.dto.*;
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

    // Dashboard and Profile Endpoints
    @GetMapping("/dashboard")
    public ResponseEntity<StudentDashboardResponse> getDashboard(Principal principal) {
        String rollNo = principal.getName();
        StudentDashboardResponse response = studentService.getDashboardData(rollNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileDTO> getStudentProfile(Principal principal) {
        String rollNo = principal.getName();
        StudentProfileDTO profile = studentService.getStudentProfile(rollNo);
        return ResponseEntity.ok(profile);
    }

    // Exam List Endpoints
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

    // Exam Taking Endpoints
    @PostMapping("/exams/{examId}/start")
    public ResponseEntity<ExamSessionDTO> startExam(@PathVariable Long examId, Principal principal) {
        String rollNo = principal.getName();
        ExamSessionDTO session = studentService.startExam(examId, rollNo);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/exams/session/{sessionId}")
    public ResponseEntity<ExamSessionDTO> getExamSession(@PathVariable Long sessionId, Principal principal) {
        String rollNo = principal.getName();
        ExamSessionDTO session = studentService.getExamSession(sessionId, rollNo);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/exams/session/{sessionId}/answer")
    public ResponseEntity<String> submitAnswer(@PathVariable Long sessionId, 
                                             @RequestBody SubmitAnswerRequest request, 
                                             Principal principal) {
        String rollNo = principal.getName();
        studentService.submitAnswer(sessionId, request, rollNo);
        return ResponseEntity.ok("Answer submitted successfully");
    }

    @PostMapping("/exams/session/{sessionId}/submit")
    public ResponseEntity<ExamResultDTO> submitExam(@PathVariable Long sessionId, Principal principal) {
        String rollNo = principal.getName();
        ExamResultDTO result = studentService.submitExam(sessionId, rollNo);
        return ResponseEntity.ok(result);
    }

    // Results Endpoints
    @GetMapping("/results")
    public ResponseEntity<List<ExamResultDTO>> getAllResults(Principal principal) {
        String rollNo = principal.getName();
        List<ExamResultDTO> results = studentService.getAllResults(rollNo);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/results/{sessionId}")
    public ResponseEntity<ExamResultDTO> getExamResult(@PathVariable Long sessionId, Principal principal) {
        String rollNo = principal.getName();
        ExamResultDTO result = studentService.getExamResult(sessionId, rollNo);
        return ResponseEntity.ok(result);
    }

    // Utility Endpoints
    @PostMapping("/exams/session/{sessionId}/save-progress")
    public ResponseEntity<String> saveProgress(@PathVariable Long sessionId, Principal principal) {
        String rollNo = principal.getName();
        studentService.saveProgress(sessionId, rollNo);
        return ResponseEntity.ok("Progress saved successfully");
    }

    @GetMapping("/exams/session/{sessionId}/time-remaining")
    public ResponseEntity<Integer> getTimeRemaining(@PathVariable Long sessionId, Principal principal) {
        String rollNo = principal.getName();
        int timeRemaining = studentService.getTimeRemaining(sessionId, rollNo);
        return ResponseEntity.ok(timeRemaining);
    }
}