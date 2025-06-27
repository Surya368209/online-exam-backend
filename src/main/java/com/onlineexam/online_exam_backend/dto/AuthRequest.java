package com.onlineexam.online_exam_backend.dto;

public class AuthRequest {
    private String rollNo;
    private String password;
    // Getters & setters
    public String getRollNo() {
        return rollNo;
    }
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

