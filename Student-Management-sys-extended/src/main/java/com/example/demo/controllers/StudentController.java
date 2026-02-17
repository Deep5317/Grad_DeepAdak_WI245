package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.StudentDao;
import com.example.demo.entites.Student;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	 StudentDao sd;
	 @GetMapping
	    public List<Student> getAllStudents() {
	        return sd.findAll();
	    }
	 @GetMapping("/{regNo}")
	    public Student ByRegNo(@PathVariable Integer regNo) {
	        return sd.findById(regNo).orElse(null);
	    }
	 @PostMapping
	    public Student add(@RequestBody Student s) {
		 if(sd.existsById(s.getRegno()))
			    throw new RuntimeException("RegNo already exists");

		 	if(sd.existsBySchoolAndStdAndRollno(
		 	        s.getSchool(), s.getStd(), s.getRollno())) {

		 	    throw new RuntimeException(
		 	        "Roll number already exists in this school & std");
		 	}

		 	
	        return sd.save(s);
	    }
	 @PutMapping("/{regNo}")
	    public Student update(@PathVariable Integer regNo,@RequestBody Student s) {
		 if(sd.existsBySchoolAndStdAndRollno(
			        s.getSchool(), s.getStd(), s.getRollno())) {

			    throw new RuntimeException(
			        "Roll number already exists in this school & std");
			}

	        return sd.save(s);
	    }

	 @PatchMapping("/{regNo}")
	 public Student patchStudent(
	         @PathVariable Integer regNo,
	         @RequestBody Student patchData) {
		 System.out.println(patchData);
	     Student existing = sd.findById(regNo)
	             .orElseThrow(() -> new RuntimeException("Student not found"));

	     if (patchData.getName() != null)
	         existing.setName(patchData.getName());

	     if (patchData.getSchool() != null)
	         existing.setSchool(patchData.getSchool());

	     if (patchData.getGender() != null)
	         existing.setGender(patchData.getGender());

	     if (patchData.getStd() != null)
	         existing.setStd(patchData.getStd());

	     if (patchData.getRollno() != null)
	         existing.setRollno(patchData.getRollno());

	     if (patchData.getPercentage() != null)
	         existing.setPercentage(patchData.getPercentage());

	     return sd.save(existing);
	 }




	    @DeleteMapping("/{regNo}")
	    public String delete(@PathVariable Integer regNo) {
	    	if(!sd.existsById(regNo))  return "Not exist";
	        sd.deleteById(regNo);
	        return "Deleted";
	    }
	    @GetMapping("/school")
	    public List<Student> school(@RequestParam String name) {
	        return sd.findBySchool(name);
	    }
	    @GetMapping("/school/count")
	    public long countSchool(@RequestParam String name) {
	        return sd.countBySchool(name);
	    }
	    @GetMapping("/school/standard/count")
	    public long countStd(@RequestParam Integer classNo) {
	        return sd.countByStd(classNo);
	    }
	    @GetMapping("/result")
	    public List<Student> result(@RequestParam boolean pass) {
	        if(pass) 
	        return sd.findByPercentageGreaterThanEqualOrderByPercentageDesc(40);
	        else
	            return sd.findByPercentageLessThanOrderByPercentageDesc(40); 
	    }

	    @GetMapping("/strength")
	    public long strength(@RequestParam String gender,@RequestParam Integer standard) {
	        return sd.countByGenderIgnoreCaseAndStd(gender,standard);
	    }
	    @GetMapping("/schoolwise-count")
	    public Map<String, Long> schoolWise(@RequestParam Integer std) {
	    	 List<Object[]> list = sd.countBySchoolForStandard(std);

	    	    Map<String, Long> result = new HashMap<>();

	    	    for(Object[] obj : list) {
	    	        result.put((String)obj[0], (Long)obj[1]);
	    	    }

	    	    return result;
	    }

}

