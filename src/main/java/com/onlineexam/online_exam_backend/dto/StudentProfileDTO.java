package com.onlineexam.online_exam_backend.dto;

public class StudentProfileDTO {
    private String rollNo;
    private String fullName;
    private String department;
    private String year;
    private int totalExamsAssigned;
    private int totalExamsCompleted;
    private double averageScore;

    // Constructors
    public StudentProfileDTO() {}

    public StudentProfileDTO(String rollNo, String fullName, String department, String year,
                           int totalExamsAssigned, int totalExamsCompleted, double averageScore) {
        this.rollNo = rollNo;
        this.fullName = fullName;
        this.department = department;
        this.year = year;
        this.totalExamsAssigned = totalExamsAssigned;
        this.totalExamsCompleted = totalExamsCompleted;
        this.averageScore = averageScore;
    }

    // Getters and Setters
    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTotalExamsAssigned() {
        return totalExamsAssigned;
    }

    public void setTotalExamsAssigned(int totalExamsAssigned) {
        this.totalExamsAssigned = totalExamsAssigned;
    }

    public int getTotalExamsCompleted() {
        return totalExamsCompleted;
    }

    public void setTotalExamsCompleted(int totalExamsCompleted) {
        this.totalExamsCompleted = totalExamsCompleted;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
}