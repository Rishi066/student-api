package com.macuniv.student_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student,Long>
{
    @Query("select s from Student s where upper(s.name) like upper(concat('%',?1,'%'))")
    List<Student> findByNameIgnoreCase(String name);
}
