package com.onlineexam.online_exam_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_sessions")
public class ExamSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "time_remaining_minutes")
    private Integer timeRemainingMinutes;

    @Column(name = "is_submitted", nullable = false)
    private Boolean isSubmitted = false;

    @Column(name = "auto_submitted", nullable = false)
    private Boolean autoSubmitted = false;

    @Column(name = "total_score")
    private Double totalScore;

    @Column(name = "percentage")
    private Double percentage;

    @OneToMany(mappedBy = "examSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAnswer> studentAnswers = new ArrayList<>();

    // Constructors
    public ExamSession() {}

    public ExamSession(Student student, Exam exam) {
        this.student = student;
        this.exam = exam;
        this.startedAt = LocalDateTime.now();
        this.timeRemainingMinutes = exam.getDurationMinutes();
        this.isSubmitted = false;
        this.autoSubmitted = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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

    public Integer getTimeRemainingMinutes() {
        return timeRemainingMinutes;
    }

    public void setTimeRemainingMinutes(Integer timeRemainingMinutes) {
        this.timeRemainingMinutes = timeRemainingMinutes;
    }

    public Boolean getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Boolean isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public Boolean getAutoSubmitted() {
        return autoSubmitted;
    }

    public void setAutoSubmitted(Boolean autoSubmitted) {
        this.autoSubmitted = autoSubmitted;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
}