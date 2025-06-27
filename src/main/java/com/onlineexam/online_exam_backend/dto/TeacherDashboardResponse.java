package com.onlineexam.online_exam_backend.dto;

import java.util.List;

public class TeacherDashboardResponse {
    private String teacherName;
    private int totalExamsCreated;
    private int totalPendingSubmissions;
    private List<String> recentExamTitles;

    // Constructors
    public TeacherDashboardResponse() {}

    public TeacherDashboardResponse(String teacherName, int totalExamsCreated, int totalPendingSubmissions, List<String> recentExamTitles) {
        this.teacherName = teacherName;
        this.totalExamsCreated = totalExamsCreated;
        this.totalPendingSubmissions = totalPendingSubmissions;
        this.recentExamTitles = recentExamTitles;
    }

    // Getters & Setters
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTotalExamsCreated() {
        return totalExamsCreated;
    }

    public void setTotalExamsCreated(int totalExamsCreated) {
        this.totalExamsCreated = totalExamsCreated;
    }

    public int getTotalPendingSubmissions() {
        return totalPendingSubmissions;
    }

    public void setTotalPendingSubmissions(int totalPendingSubmissions) {
        this.totalPendingSubmissions = totalPendingSubmissions;
    }

    public List<String> getRecentExamTitles() {
        return recentExamTitles;
    }

    public void setRecentExamTitles(List<String> recentExamTitles) {
        this.recentExamTitles = recentExamTitles;
    }
}
