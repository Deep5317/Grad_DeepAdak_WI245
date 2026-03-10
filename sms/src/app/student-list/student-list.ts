import { Component, Input } from '@angular/core';
import { StudentService } from '../services/student-service';

@Component({
  selector: 'app-student-list',
  standalone: false,
  templateUrl: './student-list.html',
  styleUrl: './student-list.css',
})
export class StudentList {
  students:any=[]
  @Input() admin=false
  editingStudent:any=null
  constructor(private service:StudentService){}

  ngOnInit(){
    this.students=this.service.getStudents()
  }

  delete(regNo:number){
    this.service.deleteStudent(regNo)
  }
  edit(student:any){
    this.editingStudent={...student}
  }

  update(){
    this.service.updateStudent(this.editingStudent)
    this.editingStudent=null
  }
}
