package com.onlineexam.online_exam_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AssignmentResponseDto {
    private UUID studentId;
    private String studentName;
    private String department;
    private LocalDateTime assignedAt;

    public AssignmentResponseDto(UUID studentId, String studentName, String department, LocalDateTime assignedAt) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.department = department;
        this.assignedAt = assignedAt;
    }

    // Getters and setters or use Lombok

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

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

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }


}