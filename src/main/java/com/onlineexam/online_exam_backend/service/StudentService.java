package com.onlineexam.online_exam_backend.service;

import com.onlineexam.online_exam_backend.dto.*;
import com.onlineexam.online_exam_backend.entity.*;
import com.onlineexam.online_exam_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamAssignmentRepository examAssignmentRepository;

    @Autowired
    private ExamSessionRepository examSessionRepository;

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

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

        // Sample announcements
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

    public ExamSessionDTO startExam(Long examId, String rollNo) {
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

        // Validate exam timing
        if (now.isBefore(exam.getStartTime())) {
            throw new RuntimeException("Exam has not started yet");
        }

        if (now.isAfter(exam.getEndTime())) {
            throw new RuntimeException("Exam has already ended");
        }

        // Check if student already has an active session
        Optional<ExamSession> existingSession = examSessionRepository
            .findByStudentAndExamAndIsSubmittedFalse(student, exam);

        ExamSession session;
        if (existingSession.isPresent()) {
            session = existingSession.get();
            // Update time remaining
            long minutesElapsed = ChronoUnit.MINUTES.between(session.getStartedAt(), now);
            int timeRemaining = Math.max(0, exam.getDurationMinutes() - (int) minutesElapsed);
            session.setTimeRemainingMinutes(timeRemaining);
        } else {
            // Create new session
            session = new ExamSession(student, exam);
            session = examSessionRepository.save(session);
        }

        return convertToExamSessionDTO(session);
    }

    public ExamSessionDTO getExamSession(Long sessionId, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamSession session = examSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Exam session not found"));

        if (!session.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Unauthorized access to exam session");
        }

        return convertToExamSessionDTO(session);
    }

    public void submitAnswer(Long sessionId, SubmitAnswerRequest request, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamSession session = examSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Exam session not found"));

        if (!session.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Unauthorized access to exam session");
        }

        if (session.getIsSubmitted()) {
            throw new RuntimeException("Exam has already been submitted");
        }

        Question question = questionRepository.findById(request.getQuestionId())
            .orElseThrow(() -> new RuntimeException("Question not found"));

        // Find or create student answer
        Optional<StudentAnswer> existingAnswer = studentAnswerRepository
            .findByExamSessionAndQuestion(session, question);

        StudentAnswer answer;
        if (existingAnswer.isPresent()) {
            answer = existingAnswer.get();
            answer.setSelectedAnswer(request.getSelectedAnswer());
        } else {
            answer = new StudentAnswer(session, question, request.getSelectedAnswer());
        }

        answer.setTimeSpentSeconds(request.getTimeSpentSeconds());
        studentAnswerRepository.save(answer);
    }

    public ExamResultDTO submitExam(Long sessionId, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamSession session = examSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Exam session not found"));

        if (!session.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Unauthorized access to exam session");
        }

        if (session.getIsSubmitted()) {
            throw new RuntimeException("Exam has already been submitted");
        }

        // Calculate results
        List<StudentAnswer> answers = studentAnswerRepository.findByExamSession(session);
        long correctAnswers = studentAnswerRepository.countByExamSessionAndIsCorrectTrue(session);
        long totalQuestions = session.getExam().getQuestions().size();
        
        double score = (double) correctAnswers / totalQuestions * session.getExam().getTotalMarks();
        double percentage = (double) correctAnswers / totalQuestions * 100;

        // Update session
        session.setIsSubmitted(true);
        session.setSubmittedAt(LocalDateTime.now());
        session.setTotalScore(score);
        session.setPercentage(percentage);
        examSessionRepository.save(session);

        return convertToExamResultDTO(session);
    }

    public List<ExamResultDTO> getAllResults(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ExamSession> completedSessions = examSessionRepository
            .findByStudentAndIsSubmittedTrue(student);

        return completedSessions.stream()
            .map(this::convertToExamResultDTO)
            .sorted((a, b) -> b.getSubmittedAt().compareTo(a.getSubmittedAt()))
            .collect(Collectors.toList());
    }

    public ExamResultDTO getExamResult(Long sessionId, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamSession session = examSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Exam session not found"));

        if (!session.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Unauthorized access to exam session");
        }

        if (!session.getIsSubmitted()) {
            throw new RuntimeException("Exam has not been submitted yet");
        }

        return convertToExamResultDTO(session);
    }

    public StudentProfileDTO getStudentProfile(String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ExamAssignment> assignments = examAssignmentRepository.findByStudent(student);
        int totalAssigned = assignments.size();
        
        List<ExamSession> completedSessions = examSessionRepository
            .findByStudentAndIsSubmittedTrue(student);
        int completed = completedSessions.size();

        double averageScore = completedSessions.stream()
            .mapToDouble(ExamSession::getPercentage)
            .average()
            .orElse(0.0);

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

    public void saveProgress(Long sessionId, String rollNo) {
        // Progress is automatically saved when answers are submitted
        // This endpoint can be used for additional progress tracking if needed
    }

    public int getTimeRemaining(Long sessionId, String rollNo) {
        Student student = studentRepository.findByUserRollNo(rollNo);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        ExamSession session = examSessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Exam session not found"));

        if (!session.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Unauthorized access to exam session");
        }

        LocalDateTime now = LocalDateTime.now();
        long minutesElapsed = ChronoUnit.MINUTES.between(session.getStartedAt(), now);
        int timeRemaining = Math.max(0, session.getExam().getDurationMinutes() - (int) minutesElapsed);
        
        return timeRemaining;
    }

    // Helper methods
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

    private ExamSessionDTO convertToExamSessionDTO(ExamSession session) {
        Exam exam = session.getExam();
        List<StudentAnswer> answers = studentAnswerRepository.findByExamSession(session);
        
        List<ExamQuestionDTO> questionDTOs = exam.getQuestions().stream()
            .map(question -> {
                Optional<StudentAnswer> answer = answers.stream()
                    .filter(a -> a.getQuestion().getId().equals(question.getId()))
                    .findFirst();
                
                return new ExamQuestionDTO(
                    question.getId(),
                    question.getQuestionText(),
                    question.getImagePath(),
                    question.getOptionA(),
                    question.getOptionB(),
                    question.getOptionC(),
                    question.getOptionD(),
                    answer.map(StudentAnswer::getSelectedAnswer).orElse(null),
                    answer.isPresent() && answer.get().getSelectedAnswer() != null
                );
            })
            .collect(Collectors.toList());

        int answeredQuestions = (int) answers.stream()
            .filter(a -> a.getSelectedAnswer() != null)
            .count();

        return new ExamSessionDTO(
            session.getId(),
            exam.getId(),
            exam.getTitle(),
            exam.getSubject(),
            exam.getQuestions().size(),
            answeredQuestions,
            session.getTimeRemainingMinutes(),
            session.getStartedAt(),
            session.getIsSubmitted(),
            questionDTOs
        );
    }

    private ExamResultDTO convertToExamResultDTO(ExamSession session) {
        Exam exam = session.getExam();
        List<StudentAnswer> answers = studentAnswerRepository.findByExamSession(session);
        
        int totalQuestions = exam.getQuestions().size();
        int correctAnswers = (int) studentAnswerRepository.countByExamSessionAndIsCorrectTrue(session);
        int answeredQuestions = (int) studentAnswerRepository.countByExamSessionAndSelectedAnswerIsNotNull(session);
        int wrongAnswers = answeredQuestions - correctAnswers;
        int unanswered = totalQuestions - answeredQuestions;
        
        long timeTakenMinutes = ChronoUnit.MINUTES.between(session.getStartedAt(), session.getSubmittedAt());

        ExamResultDTO result = new ExamResultDTO();
        result.setSessionId(session.getId());
        result.setExamTitle(exam.getTitle());
        result.setSubject(exam.getSubject());
        result.setTeacherName(exam.getTeacher().getName());
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setWrongAnswers(wrongAnswers);
        result.setUnanswered(unanswered);
        result.setTotalScore(session.getTotalScore());
        result.setPercentage(session.getPercentage());
        result.setTotalMarks(exam.getTotalMarks());
        result.setStartedAt(session.getStartedAt());
        result.setSubmittedAt(session.getSubmittedAt());
        result.setTimeTakenMinutes((int) timeTakenMinutes);
        result.setAutoSubmitted(session.getAutoSubmitted());
        
        return result;
    }
}