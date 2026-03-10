import { Injectable } from '@angular/core';
import { Student } from '../student.model';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  students:Student[]=[]

  addStudent(s:Student){
    this.students.push(s)
  }

  getStudents(){
    return this.students
  }

  deleteStudent(regNo:number){
    this.students=this.students.filter(s=>s.regNo!=regNo)
  }
   updateStudent(updatedStudent:Student){
    const index=this.students.findIndex(s=>s.regNo==updatedStudent.regNo)
    if(index!=-1){
      this.students[index]=updatedStudent
    }
  }
}
