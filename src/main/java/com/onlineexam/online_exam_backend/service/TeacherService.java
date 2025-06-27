package com.onlineexam.online_exam_backend.service;

import com.onlineexam.online_exam_backend.dto.AddQuestionRequest;
import com.onlineexam.online_exam_backend.dto.AssignmentResponseDto;
import com.onlineexam.online_exam_backend.dto.CreateExamRequest;
import com.onlineexam.online_exam_backend.dto.ExamResponseDTO;
import com.onlineexam.online_exam_backend.dto.QuestionResponseDTO;
import com.onlineexam.online_exam_backend.dto.TeacherDashboardResponse;
import com.onlineexam.online_exam_backend.entity.Exam;
import com.onlineexam.online_exam_backend.entity.ExamAssignment;
import com.onlineexam.online_exam_backend.entity.Question;
import com.onlineexam.online_exam_backend.entity.Student;
import com.onlineexam.online_exam_backend.entity.Teacher;
import com.onlineexam.online_exam_backend.repository.ExamAssignmentRepository;
import com.onlineexam.online_exam_backend.repository.ExamRepository;
import com.onlineexam.online_exam_backend.repository.TeacherRepository;

import com.onlineexam.online_exam_backend.repository.QuestionRepository;
import com.onlineexam.online_exam_backend.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamAssignmentRepository examAssignmentRepository;

    public TeacherDashboardResponse getDashboardData(String rollNo) {
        Teacher teacher = teacherRepository.findByUserRollNo(rollNo);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        List<Exam> exams = examRepository.findByTeacher(teacher);
        List<String> recentTitles = exams.stream()
            .sorted((a, b) -> b.getStartTime().compareTo(a.getStartTime())) // newest first
            .limit(5)
            .map(Exam::getTitle)
            .collect(Collectors.toList());

        TeacherDashboardResponse response = new TeacherDashboardResponse();
        response.setTeacherName(teacher.getName());
        response.setTotalExamsCreated(exams.size());
        response.setTotalPendingSubmissions(0); // Placeholder for future extension
        response.setRecentExamTitles(recentTitles);

        return response;
    }

    public void createExam(CreateExamRequest request, String rollNo) {
        Teacher teacher = teacherRepository.findByUserRollNo(rollNo);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setSubject(request.getSubject());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());
        exam.setTeacher(teacher);

        examRepository.save(exam);
    }

    public List<ExamResponseDTO> getAllExamsByTeacher(String rollNo) {
        Teacher teacher = teacherRepository.findByUserRollNo(rollNo);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        List<Exam> exams = examRepository.findByTeacher(teacher);
            return exams.stream().map(exam -> {
                ExamResponseDTO dto = new ExamResponseDTO();
                dto.setTitle(exam.getTitle());
                dto.setSubject(exam.getSubject());
                dto.setTotalMarks(exam.getTotalMarks());
                dto.setDurationMinutes(exam.getDurationMinutes());
                dto.setStartTime(exam.getStartTime());
                dto.setEndTime(exam.getEndTime());
                return dto;
        }).collect(Collectors.toList());
    }

    public void addQuestionToExam(Long examId, AddQuestionRequest request, String rollNo) {
        Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new RuntimeException("Exam not found"));

        // Ensure the logged-in teacher owns this exam
        if (!exam.getTeacher().getUser().getRollNo().equals(rollNo)) {
            throw new RuntimeException("Unauthorized access to exam");
        }

        Question question = new Question();
        question.setExam(exam);
        question.setQuestionText(request.getQuestionText());
        question.setImagePath(request.getImagePath());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());

        questionRepository.save(question);
    }
    public List<QuestionResponseDTO> getQuestionsForExam(Long examId, String rollNo) {
        Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new RuntimeException("Exam not found"));

        if (!exam.getTeacher().getUser().getRollNo().equals(rollNo)) {
            throw new RuntimeException("Unauthorized access to exam");
        }

        List<Question> questions = exam.getQuestions();

        return questions.stream().map(q -> {
            QuestionResponseDTO dto = new QuestionResponseDTO();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setImagePath(q.getImagePath());
            dto.setOptionA(q.getOptionA());
            dto.setOptionB(q.getOptionB());
            dto.setOptionC(q.getOptionC());
            dto.setOptionD(q.getOptionD());
            dto.setCorrectAnswer(q.getCorrectAnswer());
            return dto;
        }).collect(Collectors.toList());
    }


public void assignExamToDepartmentAndYearStudents(Long examId, String department, String year) {
    Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new RuntimeException("Exam not found"));

    List<Student> students = studentRepository.findByDepartmentAndYear(department, year);

    for (Student student : students) {
        ExamAssignment assignment = new ExamAssignment();
        assignment.setExam(exam);
        assignment.setStudent(student);
        assignment.setDepartment(department);
        assignment.setYear(year);
        assignment.setAssignedAt(LocalDateTime.now());
        examAssignmentRepository.save(assignment);
    }
}
public List<AssignmentResponseDto> getExamAssignmentsByExamId(Long examId) {
    Exam exam = examRepository.findById(examId)
        .orElseThrow(() -> new RuntimeException("Exam not found"));

    List<ExamAssignment> assignments = examAssignmentRepository.findByExam(exam);

    return assignments.stream().map(assignment -> {
        Student student = assignment.getStudent();
        return new AssignmentResponseDto(
            student.getId(),
            student.getFullName(),
            student.getDepartment(),
            assignment.getAssignedAt()
        );
    }).collect(Collectors.toList());
}


}
