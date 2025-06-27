package com.onlineexam.online_exam_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_answers")
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_session_id", nullable = false)
    private ExamSession examSession;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_answer", length = 1)
    private String selectedAnswer; // "A", "B", "C", "D", or null if not answered

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;

    @Column(name = "time_spent_seconds")
    private Integer timeSpentSeconds;

    // Constructors
    public StudentAnswer() {}

    public StudentAnswer(ExamSession examSession, Question question, String selectedAnswer) {
        this.examSession = examSession;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.answeredAt = LocalDateTime.now();
        this.isCorrect = selectedAnswer != null && selectedAnswer.equals(question.getCorrectAnswer());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
        this.isCorrect = selectedAnswer != null && selectedAnswer.equals(question.getCorrectAnswer());
        this.answeredAt = LocalDateTime.now();
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }

    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(Integer timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }
}