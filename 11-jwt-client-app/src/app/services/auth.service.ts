import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Login, User } from '../models/user';
import { map } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl = environment.apiBaseURL+'/api/auth'
  constructor(private http: HttpClient, private tokenService: TokenService) { }

  signup(user: User){
    return this.http.post<any>(`${this.apiUrl}/register`, user)
  }

  login(data: Login){
    return this.http.post<any>(`${this.apiUrl}/login`, data) 
                                              .pipe(map((response)=>{
                                                if(response.token){
                                                  this.tokenService.setToken(response.token, response.role)
                                                }
                                                return response;
                                              }
                                              ))
                                                          
  }

  logout(){
    this.tokenService.removeToken();
  }
}
