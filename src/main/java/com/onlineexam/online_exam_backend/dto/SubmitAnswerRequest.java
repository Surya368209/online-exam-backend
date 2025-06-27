package com.onlineexam.online_exam_backend.dto;

public class SubmitAnswerRequest {
    private Long questionId;
    private String selectedAnswer; // "A", "B", "C", "D"
    private int timeSpentSeconds;

    // Constructors
    public SubmitAnswerRequest() {}

    public SubmitAnswerRequest(Long questionId, String selectedAnswer, int timeSpentSeconds) {
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
        this.timeSpentSeconds = timeSpentSeconds;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(int timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }
}