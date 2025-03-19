import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  myForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  })
  errorMessage: string =''

  constructor(private authService: AuthService, private router: Router, private tokenService: TokenService){
    tokenService.isLoggedIn.subscribe((value)=>{
      if(value){
        this.router.navigate(['/products'])
      }
    })
  }

  handleSubmit(){
    const {username, password} = this.myForm.value
    if(username && password){
      this.authService.login({username, password}).subscribe({
        next: (response)=>{
          if(response && response.token){
            this.router.navigate(['/products'])
          }
        },
        error: (error)=>{
          this.errorMessage = error.message;
        }
      })
    }else{
      this.errorMessage = "Please check username or password"
    }
  }
}
