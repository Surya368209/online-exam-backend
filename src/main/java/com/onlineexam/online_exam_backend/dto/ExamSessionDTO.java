package com.onlineexam.online_exam_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ExamSessionDTO {
    private Long sessionId;
    private Long examId;
    private String examTitle;
    private String subject;
    private int totalQuestions;
    private int answeredQuestions;
    private int timeRemainingMinutes;
    private LocalDateTime startedAt;
    private boolean isSubmitted;
    private List<ExamQuestionDTO> questions;

    // Constructors
    public ExamSessionDTO() {}

    public ExamSessionDTO(Long sessionId, Long examId, String examTitle, String subject,
                         int totalQuestions, int answeredQuestions, int timeRemainingMinutes,
                         LocalDateTime startedAt, boolean isSubmitted, List<ExamQuestionDTO> questions) {
        this.sessionId = sessionId;
        this.examId = examId;
        this.examTitle = examTitle;
        this.subject = subject;
        this.totalQuestions = totalQuestions;
        this.answeredQuestions = answeredQuestions;
        this.timeRemainingMinutes = timeRemainingMinutes;
        this.startedAt = startedAt;
        this.isSubmitted = isSubmitted;
        this.questions = questions;
    }

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(int answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public int getTimeRemainingMinutes() {
        return timeRemainingMinutes;
    }

    public void setTimeRemainingMinutes(int timeRemainingMinutes) {
        this.timeRemainingMinutes = timeRemainingMinutes;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public List<ExamQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ExamQuestionDTO> questions) {
        this.questions = questions;
    }
}