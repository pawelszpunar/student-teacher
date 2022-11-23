package com.dev.teacherstudent.repository;

import com.dev.teacherstudent.entity.StudentsEntity;
import com.dev.teacherstudent.entity.TeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<StudentsEntity, Long>, JpaSpecificationExecutor<StudentsEntity> {

    @Query(name = "SELECT * FROM STUDENTS WHERE name = :name OR surname = :surname", nativeQuery = true)
    List<StudentsEntity> findAllByNameOrSurname(@Param("name")String name, @Param("surname") String surname);

}
