package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Student {
	@Id
	private int rollno;
	private String name;
	private int std;
	private String school;
	
	
	public Student() {
		
	}
	public Student(int rollno, String name, int std, String school) {
		this.rollno = rollno;
		this.name = name;
		this.std = std;
		this.school = school;
	}
	@Override
	public String toString() {
		return "Student [rollno=" + rollno + ", name=" + name + ", std=" + std + ", school=" + school + "]";
	}
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStd() {
		return std;
	}
	public void setStd(int std) {
		this.std = std;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	

}
