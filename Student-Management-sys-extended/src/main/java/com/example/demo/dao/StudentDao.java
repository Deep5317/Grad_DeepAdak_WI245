package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entites.Student;

public interface StudentDao extends JpaRepository<Student, Integer> {
	List<Student> findBySchool(String school);
	long countBySchool(String school);
	long countByStd(int std);
	List<Student> findByPercentageGreaterThanEqualOrderByPercentageDesc(double marks);

    List<Student> findByPercentageLessThanOrderByPercentageDesc(double marks);
    long countByGenderIgnoreCaseAndStd(String gender, int std);
    @Query("SELECT s.school, COUNT(s) FROM Student s WHERE s.std = :std GROUP BY s.school")
    List<Object[]> countBySchoolForStandard(@Param("std") int std);
    boolean existsBySchoolAndStdAndRollno(
    	    String school, int std, int rollno);


}
