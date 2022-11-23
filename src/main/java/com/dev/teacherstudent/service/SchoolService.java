package com.dev.teacherstudent.service;

import com.dev.teacherstudent.entity.StudentTeacherEntity;
import com.dev.teacherstudent.entity.StudentsEntity;
import com.dev.teacherstudent.entity.TeachersEntity;
import com.dev.teacherstudent.repository.StudentsRepository;
import com.dev.teacherstudent.repository.StudentsTeachersRepository;
import com.dev.teacherstudent.repository.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

    private final StudentsRepository studentsRepository;
    private final TeachersRepository teachersRepository;
    private final StudentsTeachersRepository studentsTeachersRepository;

    @Autowired
    public SchoolService(StudentsRepository studentsRepository, TeachersRepository teachersRepository, StudentsTeachersRepository studentsTeachersRepository) {
        this.studentsRepository = studentsRepository;
        this.teachersRepository = teachersRepository;
        this.studentsTeachersRepository = studentsTeachersRepository;
    }

    public List<StudentsEntity> getAllStudents() {
        return studentsRepository.findAll();
    }

    public List<TeachersEntity> getAllTeachers() {
        return teachersRepository.findAll();
    }

    public Page<StudentsEntity> getAllStudents(Pageable pageable) {
        return studentsRepository.findAll(pageable);
    }

    public Page<TeachersEntity> getAllTeachers(Pageable pageable) {
        return teachersRepository.findAll(pageable);
    }

    public StudentsEntity saveStudent(StudentsEntity studentsEntity) {
        return studentsRepository.saveAndFlush(studentsEntity);
    }

    public TeachersEntity saveTeacher(TeachersEntity teachersEntity) {
        return teachersRepository.saveAndFlush(teachersEntity);
    }

    public boolean deleteStudent(Long id) {
        if(studentsRepository.findById(id).isPresent()) {
            studentsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteTeacher(Long id) {
        if(teachersRepository.findById(id).isPresent()) {
            teachersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<StudentsEntity> findStudentById(Long id) {
        return studentsRepository.findById(id);
    }

    public Optional<TeachersEntity> findTeacherById(Long id) {
        return teachersRepository.findById(id);
    }

    public void addStudentTeacher(StudentTeacherEntity studentTeacher) {
        studentsTeachersRepository.saveAndFlush(studentTeacher);
    }

    public ResponseEntity<String> deleteStudentTeacher(StudentsEntity student, TeachersEntity teacher){
        try {
            StudentTeacherEntity studentTeacher = studentsTeachersRepository.findByStudentAndTeacher(student, teacher).get();
            studentsTeachersRepository.delete(studentTeacher);
        } catch (Exception e) {
            return new ResponseEntity<>("relation not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<StudentsEntity> findStudentsByFirstNameOrSurname(String searchTerm, String searchTerm1) {
        return studentsRepository.findAllByNameOrSurname(searchTerm, searchTerm1);
    }

    public List<TeachersEntity> findTeachersByFirstNameOrSurname(String searchTerm, String searchTerm1) {
        return teachersRepository.findAllByNameOrSurname(searchTerm, searchTerm1);
    }

    public List<Optional<TeachersEntity>> findTeachersAssignedToStudentById(Long id) {
        List<Optional<TeachersEntity>> teachers = new ArrayList<>();
        List<StudentTeacherEntity> list = studentsTeachersRepository.findAllByStudent_id(id);
        for(StudentTeacherEntity temp: list) {
            teachers.add(teachersRepository.findById(temp.getTeacher().getId()));
        }
        return teachers;
    }

    public List<Optional<StudentsEntity>> findStudentsAssignedToTeacherById(Long id) {
        List<Optional<StudentsEntity>> students = new ArrayList<>();
        List<StudentTeacherEntity> list = studentsTeachersRepository.findAllByTeacher_id(id);
        for(StudentTeacherEntity temp: list) {
            students.add(studentsRepository.findById(temp.getStudent().getId()));
        }
        return students;
    }
}
