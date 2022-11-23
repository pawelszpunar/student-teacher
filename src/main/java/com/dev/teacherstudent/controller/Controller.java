package com.dev.teacherstudent.controller;

import com.dev.teacherstudent.entity.StudentTeacherEntity;
import com.dev.teacherstudent.entity.StudentsEntity;
import com.dev.teacherstudent.entity.TeachersEntity;
import com.dev.teacherstudent.pagination.ListResponse;
import com.dev.teacherstudent.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    private final SchoolService schoolService;

    @Autowired
    public Controller(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // all students without poagination
    @GetMapping("/api/allstudents")
    public List<StudentsEntity> getStudents() {
        return schoolService.getAllStudents();
    }

    // all students with pagination
    @GetMapping("/api/students")
    public ListResponse getStudentsPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentsEntity> studentPage = schoolService.getAllStudents(pageable);

        return new ListResponse(
                studentPage.getContent(),
                studentPage.getTotalPages(),
                studentPage.getTotalElements(),
                studentPage.getSize(),
                studentPage.getNumber()
        );
    }

    // all teachers without pagination
    @GetMapping("/api/allteachers")
    public List<TeachersEntity> getTeachers() {
        return schoolService.getAllTeachers();
    }

    // all teachers with pagination
    @GetMapping("/api/teachers")
    public ListResponse getTeachersPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TeachersEntity> teacherPage = schoolService.getAllTeachers(pageable);

        return new ListResponse(
                teacherPage.getContent(),
                teacherPage.getTotalPages(),
                teacherPage.getTotalElements(),
                teacherPage.getSize(),
                teacherPage.getNumber()
        );
    }

    // add new student
    @PostMapping("/api/students")
    public StudentsEntity newStudent(@Valid @RequestBody StudentsEntity studentsEntity, Errors errors) throws Exception {
//        if (errors.hasErrors()) {
//            throw new Exception(errors.);
//        }

        return schoolService.saveStudent(studentsEntity);
    }

    // add new teacher
    @PostMapping("/api/teachers")
    public TeachersEntity newTeacher(@Valid @RequestBody TeachersEntity teachersEntity) {
        return schoolService.saveTeacher(teachersEntity);
    }

    // delete student
    @DeleteMapping("/api/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        boolean isRemoved = schoolService.deleteStudent(id);
        if(!isRemoved) {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // delete teacher
    @DeleteMapping("/api/teachers/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        boolean isRemoved = schoolService.deleteTeacher(id);
        if(!isRemoved) {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // add student to teacher
    @PostMapping("/api/student/{student_id}/teacher/{teacher_id}")
    public ResponseEntity<String> addStudentToTeacher(
            @PathVariable Long student_id,
            @PathVariable Long teacher_id) throws Exception {
        StudentsEntity student = schoolService.findStudentById(student_id).orElseThrow(() -> new Exception("student not found"));
        TeachersEntity teacher = schoolService.findTeacherById(teacher_id).orElseThrow(() -> new Exception("teacher not found"));
        StudentTeacherEntity studentTeacher = new StudentTeacherEntity(student, teacher);
        schoolService.addStudentTeacher(studentTeacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // delete student-teacher relation
    @DeleteMapping("/api/student/{student_id}/teacher/{teacher_id}")
    public ResponseEntity<String> deleteStudentToTeacher(
            @PathVariable Long student_id,
            @PathVariable Long teacher_id) throws Exception {
        StudentsEntity student = schoolService.findStudentById(student_id).orElseThrow(() -> new Exception("student not found"));
        TeachersEntity teacher = schoolService.findTeacherById(teacher_id).orElseThrow(() -> new Exception("teacher not found"));
        return schoolService.deleteStudentTeacher(student, teacher);
    }

    // search for students by name or surname
    @GetMapping("/api/students/search/{searchTerm}")
    public List<StudentsEntity> searchStudentsByFirstNameOrSurname(@PathVariable String searchTerm) {
        return schoolService.findStudentsByFirstNameOrSurname(searchTerm, searchTerm);
    }

    // search for teachers by name or surname
    @GetMapping("/api/teachers/search/{searchTerm}")
    public List<TeachersEntity> searchTeachersByFirstNameOrSurname(@PathVariable String searchTerm) {
        return schoolService.findTeachersByFirstNameOrSurname(searchTerm, searchTerm);
    }

    // find teachers related to student
    @GetMapping("/api/teachers/tostudent/{id}")
    public List<Optional<TeachersEntity>> findTeachersAssignedToStudentById(@PathVariable Long id) {
        return schoolService.findTeachersAssignedToStudentById(id);
    }

    // find students related to teacher
    @GetMapping("/api/students/toteacher/{id}")
    public List<Optional<StudentsEntity>> findStudentsAssignedToTeacherById(@PathVariable Long id) {
        return schoolService.findStudentsAssignedToTeacherById(id);
    }

}
