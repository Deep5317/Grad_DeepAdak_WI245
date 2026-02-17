package com.example.demo.dao.pg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;
@Repository
public interface StudentPgSqlDao extends JpaRepository<Student,Integer>{

}
