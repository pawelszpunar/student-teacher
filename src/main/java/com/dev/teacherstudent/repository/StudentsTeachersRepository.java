package com.dev.teacherstudent.repository;

import com.dev.teacherstudent.entity.StudentTeacherEntity;
import com.dev.teacherstudent.entity.StudentsEntity;
import com.dev.teacherstudent.entity.TeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsTeachersRepository extends JpaRepository<StudentTeacherEntity, Long>, JpaSpecificationExecutor<StudentTeacherEntity> {

    Optional<StudentTeacherEntity> findByStudentAndTeacher(StudentsEntity student, TeachersEntity teacher);

    List<StudentTeacherEntity> findAllByStudent_id(Long id);

    List<StudentTeacherEntity> findAllByTeacher_id(Long id);
}
