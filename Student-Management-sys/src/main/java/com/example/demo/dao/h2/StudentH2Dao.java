package com.example.demo.dao.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;
@Repository
public interface StudentH2Dao extends JpaRepository<Student,Integer>{

}
