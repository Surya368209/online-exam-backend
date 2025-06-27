package com.onlineexam.online_exam_backend.controller;

import com.onlineexam.online_exam_backend.dto.AddQuestionRequest;
import com.onlineexam.online_exam_backend.dto.AssignExamByDepartmentRequest;
import com.onlineexam.online_exam_backend.dto.AssignmentResponseDto;
import com.onlineexam.online_exam_backend.dto.CreateExamRequest;
import com.onlineexam.online_exam_backend.dto.ExamResponseDTO;
import com.onlineexam.online_exam_backend.dto.TeacherDashboardResponse;
// import com.onlineexam.online_exam_backend.entity.Exam;
import com.onlineexam.online_exam_backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/dashboard")
    public ResponseEntity<TeacherDashboardResponse> getDashboard(Principal principal) {
        String rollNo = principal.getName(); // Set from JWT via Spring Security
        TeacherDashboardResponse response = teacherService.getDashboardData(rollNo);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/exams")
    public ResponseEntity<?> createExam(@RequestBody CreateExamRequest request, Principal principal) {
        String rollNo = principal.getName(); // Extracted from JWT
        teacherService.createExam(request, rollNo);
        return ResponseEntity.ok("Exam created successfully.");
    }



    @GetMapping("/exams")
    public ResponseEntity<List<ExamResponseDTO>> getAllExamsByTeacher(Principal principal) {
        String rollNo = principal.getName();
        List<ExamResponseDTO> exams = teacherService.getAllExamsByTeacher(rollNo);
        return ResponseEntity.ok(exams);
    }

    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<?> addQuestionToExam(@PathVariable Long examId,@RequestBody AddQuestionRequest request,Principal principal) {

        String rollNo = principal.getName(); // from JWT
        teacherService.addQuestionToExam(examId, request, rollNo);
        return ResponseEntity.ok("Question added successfully.");
    }

    @GetMapping("/exams/{examId}/questions")
    public ResponseEntity<?> getQuestionsForExam(@PathVariable Long examId, Principal principal) {
        String rollNo = principal.getName();
        return ResponseEntity.ok(teacherService.getQuestionsForExam(examId, rollNo));
    }


@PostMapping("/exams/{examId}/assign")
public ResponseEntity<String> assignExamToStudents(
        @PathVariable Long examId,
        @RequestBody AssignExamByDepartmentRequest request,
        Principal principal) {

    teacherService.assignExamToDepartmentAndYearStudents(
        examId, request.getDepartment(), request.getYear()
    );

    return ResponseEntity.ok("Exam assigned to " + request.getDepartment() + " - " + request.getYear());
}
    @GetMapping("/exams/{examId}/assignments")
    public ResponseEntity<List<AssignmentResponseDto>> getExamAssignments(@PathVariable Long examId) {
        List<AssignmentResponseDto> assignments = teacherService.getExamAssignmentsByExamId(examId);
        return ResponseEntity.ok(assignments);
    }
}
