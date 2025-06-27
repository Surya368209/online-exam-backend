// AssignExamByDepartmentRequest.java
package com.onlineexam.online_exam_backend.dto;

public class AssignExamByDepartmentRequest {
    private String department;
    private String year;

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
}