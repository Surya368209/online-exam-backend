package com.onlineexam.online_exam_backend.dto;

import java.util.List;

public class StudentDashboardResponse {
    private String studentName;
    private String department;
    private String year;
    private int totalAssignedExams;
    private int upcomingExams;
    private int completedExams;
    private int pendingExams;
    private List<StudentExamDTO> recentExams;
    private List<String> announcements;

    // Constructors
    public StudentDashboardResponse() {}

    public StudentDashboardResponse(String studentName, String department, String year, 
                                  int totalAssignedExams, int upcomingExams, int completedExams, 
                                  int pendingExams, List<StudentExamDTO> recentExams, 
                                  List<String> announcements) {
        this.studentName = studentName;
        this.department = department;
        this.year = year;
        this.totalAssignedExams = totalAssignedExams;
        this.upcomingExams = upcomingExams;
        this.completedExams = completedExams;
        this.pendingExams = pendingExams;
        this.recentExams = recentExams;
        this.announcements = announcements;
    }

    // Getters and Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public int getTotalAssignedExams() {
        return totalAssignedExams;
    }

    public void setTotalAssignedExams(int totalAssignedExams) {
        this.totalAssignedExams = totalAssignedExams;
    }

    public int getUpcomingExams() {
        return upcomingExams;
    }

    public void setUpcomingExams(int upcomingExams) {
        this.upcomingExams = upcomingExams;
    }

    public int getCompletedExams() {
        return completedExams;
    }

    public void setCompletedExams(int completedExams) {
        this.completedExams = completedExams;
    }

    public int getPendingExams() {
        return pendingExams;
    }

    public void setPendingExams(int pendingExams) {
        this.pendingExams = pendingExams;
    }

    public List<StudentExamDTO> getRecentExams() {
        return recentExams;
    }

    public void setRecentExams(List<StudentExamDTO> recentExams) {
        this.recentExams = recentExams;
    }

    public List<String> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<String> announcements) {
        this.announcements = announcements;
    }
}