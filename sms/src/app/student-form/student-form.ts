import { Component } from '@angular/core';
import { StudentService } from '../services/student-service';

@Component({
  selector: 'app-student-form',
  standalone: false,
  templateUrl: './student-form.html',
  styleUrl: './student-form.css',
})
export class StudentForm {

  student:any={}

  constructor(private service:StudentService){}

  add(){
    this.service.addStudent(this.student)
    this.student={}
  }
}
