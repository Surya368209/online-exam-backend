package com.onlineexam.online_exam_backend.dto;

import java.time.LocalDateTime;

public class StudentExamDTO {
    private Long examId;
    private String title;
    private String subject;
    private String teacherName;
    private int totalMarks;
    private int durationMinutes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime assignedAt;
    private String status; // "UPCOMING", "ONGOING", "COMPLETED", "MISSED"
    private boolean canStart;
    private int totalQuestions;

    // Constructors
    public StudentExamDTO() {}

    public StudentExamDTO(Long examId, String title, String subject, String teacherName,
                         int totalMarks, int durationMinutes, LocalDateTime startTime,
                         LocalDateTime endTime, LocalDateTime assignedAt, String status,
                         boolean canStart, int totalQuestions) {
        this.examId = examId;
        this.title = title;
        this.subject = subject;
        this.teacherName = teacherName;
        this.totalMarks = totalMarks;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignedAt = assignedAt;
        this.status = status;
        this.canStart = canStart;
        this.totalQuestions = totalQuestions;
    }

    // Getters and Setters
    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCanStart() {
        return canStart;
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}