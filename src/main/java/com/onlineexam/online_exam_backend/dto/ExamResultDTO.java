package com.onlineexam.online_exam_backend.dto;

import java.time.LocalDateTime;

public class ExamResultDTO {
    private Long sessionId;
    private String examTitle;
    private String subject;
    private String teacherName;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private int unanswered;
    private double totalScore;
    private double percentage;
    private int totalMarks;
    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;
    private int timeTakenMinutes;
    private boolean autoSubmitted;

    // Constructors
    public ExamResultDTO() {}

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(int unanswered) {
        this.unanswered = unanswered;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getTimeTakenMinutes() {
        return timeTakenMinutes;
    }

    public void setTimeTakenMinutes(int timeTakenMinutes) {
        this.timeTakenMinutes = timeTakenMinutes;
    }

    public boolean isAutoSubmitted() {
        return autoSubmitted;
    }

    public void setAutoSubmitted(boolean autoSubmitted) {
        this.autoSubmitted = autoSubmitted;
    }
}