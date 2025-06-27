package com.onlineexam.online_exam_backend.service;

import com.onlineexam.online_exam_backend.dto.AuthRequest;
import com.onlineexam.online_exam_backend.dto.AuthResponse;
import com.onlineexam.online_exam_backend.dto.ChangePasswordRequest;
import com.onlineexam.online_exam_backend.dto.RegisterRequest;
import com.onlineexam.online_exam_backend.entity.Student;
import com.onlineexam.online_exam_backend.entity.Teacher;
import com.onlineexam.online_exam_backend.entity.User;
import com.onlineexam.online_exam_backend.repository.UserRepository;
import com.onlineexam.online_exam_backend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // 1. Register user with full data (name, role, etc.)
    public String registerUser(RegisterRequest request) {
        User user = userRepository.findByRollNoIgnoreCase(request.getRollNo())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getStudent() != null || user.getTeacher() != null) {
            return "User is already registered.";
        }

        user.setRole(request.getRole());

        if ("student".equalsIgnoreCase(request.getRole())) {
            Student student = new Student();
            student.setDepartment(request.getDepartment());
            student.setFullName(request.getFullName());
            student.setYear(request.getYear());
            student.setUser(user);
            user.setStudent(student);
        } else if ("teacher".equalsIgnoreCase(request.getRole())) {
            Teacher teacher = new Teacher();
            teacher.setDepartment(request.getDepartment());
            teacher.setDesignation(request.getDesignation());
            teacher.setName(request.getName());
            teacher.setUser(user);
            user.setTeacher(teacher);
        } else {
            return "Invalid role provided.";
        }

        user.setForceChangePassword(true); // Must change password after first login
        userRepository.save(user);
        return "Registered successfully";
    }

    // 2. Login
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByRollNoIgnoreCase(request.getRollNo())
        .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String token = jwtUtil.generateToken(user.getRollNo(), user.getRole());

        return new AuthResponse(
            "Login successful",
            user.getRole(),
            user.getForceChangePassword(),
            token
        );
    }

    // 3. Change Password
    public String changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByRollNoIgnoreCase(request.getRollNo()).orElse(null);
        if (user == null) {
            return "User not found";
        }

        if (!user.getPassword().equals(request.getOldPassword())) {
            return "Old password is incorrect";
        }

        user.setPassword(request.getNewPassword());
        user.setForceChangePassword(false);
        userRepository.save(user);

        return "Password changed successfully";
    }
}