package com.dev.teacherstudent.repository;

import com.dev.teacherstudent.entity.StudentsEntity;
import com.dev.teacherstudent.entity.TeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachersRepository extends JpaRepository<TeachersEntity, Long>, JpaSpecificationExecutor<TeachersEntity> {

    @Query(name = "SELECT * FROM TEACHERS WHERE name = :name OR surname = :surname", nativeQuery = true)
    List<TeachersEntity> findAllByNameOrSurname(String searchTerm, String searchTerm1);

}
