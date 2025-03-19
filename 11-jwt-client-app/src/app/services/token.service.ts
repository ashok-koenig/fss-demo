import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  isLoggedIn = new BehaviorSubject<boolean>(false)
  userRole = new BehaviorSubject<string>('')

  constructor() { 
    if(this.getToken()){
      this.isLoggedIn.next(true)
      this.userRole.next(this.getRole())
    }
  }

  setToken(token: string, role: string){
    localStorage.setItem('login-token', token)
    localStorage.setItem('user-role', role)
    this.isLoggedIn.next(true)
    this.userRole.next(role)
  }

  getToken(){
    return localStorage.getItem('login-token')
  }

  getRole(){
    return localStorage.getItem('user-role') || "USER"
  }

  removeToken(){
    localStorage.removeItem('login-token')
    localStorage.removeItem('user-role')
    this.isLoggedIn.next(false)
    this.userRole.next('')
  }
}
