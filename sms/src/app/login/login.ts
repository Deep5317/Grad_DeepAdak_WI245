import { Component } from '@angular/core';
import { Auth } from '../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
   username=""
  password=""
  msg=""

  constructor(private auth:Auth,private router:Router){}

  login(){

    let role=this.auth.login(this.username,this.password)

    if(role=="admin")
      this.router.navigate(['/admin'])

    else if(role=="staff")
      this.router.navigate(['/staff'])

    else{
      this.router.navigate([''])
      this.msg="Invalid Login"
    }

  }
}
