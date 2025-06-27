package com.onlineexam.online_exam_backend.dto;

public class ExamQuestionDTO {
    private Long questionId;
    private String questionText;
    private String imagePath;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String selectedAnswer; // Student's current answer
    private boolean isAnswered;

    // Constructors
    public ExamQuestionDTO() {}

    public ExamQuestionDTO(Long questionId, String questionText, String imagePath,
                          String optionA, String optionB, String optionC, String optionD,
                          String selectedAnswer, boolean isAnswered) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.imagePath = imagePath;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.selectedAnswer = selectedAnswer;
        this.isAnswered = isAnswered;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}