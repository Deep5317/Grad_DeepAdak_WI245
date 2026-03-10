import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  role=""

  login(username:string,password:string){

    if(username=="admin" && password=="admin"){
      this.role="admin"
      return "admin"
    }

    if(username=="staff" && password=="staff"){
      this.role="staff"
      return "staff"
    }

    return "invalid"
}
}
