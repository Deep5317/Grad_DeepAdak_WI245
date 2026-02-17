package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.h2.StudentH2Dao;
import com.example.demo.dao.pg.StudentPgSqlDao;
import com.example.demo.entities.Student;

@Controller
public class StudentWebController {
	@Autowired
    private StudentH2Dao h2Dao;

    @Autowired
    private StudentPgSqlDao pgDao;
    @RequestMapping("/")
    public String showForm() {
        return "index.jsp";   // opens index.jsp
    }

    // Handle form submit
    @RequestMapping("/insert")
    @ResponseBody
    public String insert(Student s) {

        h2Dao.save(s);
        pgDao.save(s);

        return "success"; 
    }
	    
	    
}
