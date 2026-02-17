package com.example.demo.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Student {
	@Id
	private Integer regno;
	private Integer rollno;
	private String name;
	private Integer std;
	private String school;
	private String gender;
	private Double percentage;
	
	public Student() {
		
	}
	@Override
	public String toString() {
		return "Student [regno=" + regno + ", rollno=" + rollno + ", name=" + name + ", std=" + std + ", school="
				+ school + ", gender=" + gender + ", percentage=" + percentage + "]";
	}
	public Student(Integer regno, Integer rollno, String name, Integer std, String school, String gender, Double percentage) {
		
		this.regno = regno;
		this.rollno = rollno;
		this.name = name;
		this.std = std;
		this.school = school;
		this.gender = gender;
		this.percentage = percentage;
	}
	public Integer getRegno() {
		return regno;
	}
	public void setRegno(Integer regno) {
		this.regno = regno;
	}
	public Integer getRollno() {
		return rollno;
	}
	public void setRollno(Integer rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStd() {
		return std;
	}
	public void setStd(Integer std) {
		this.std = std;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
