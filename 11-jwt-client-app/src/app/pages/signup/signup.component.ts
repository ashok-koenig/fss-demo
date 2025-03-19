import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-signup',
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  constructor(private authService: AuthService, private tokenService: TokenService, private router: Router){
    tokenService.isLoggedIn.subscribe((value)=>{
      if(value){
        this.router.navigate(['/products'])
      }
    })
  }

  myForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    role: new FormControl('USER', Validators.required)
  })

  errorMessage: string =''

  get firstName(): FormControl {
    return this.myForm.get('firstName') as FormControl
  }

  get lastName(): FormControl {
    return this.myForm.get('lastName') as FormControl
  }

  get username(): FormControl {
    return this.myForm.get('username') as FormControl
  }

  get password(): FormControl {
    return this.myForm.get('password') as FormControl
  }

  get role(): FormControl {
    return this.myForm.get('role') as FormControl
  }

  handleSubmit(){
    console.log(this.myForm.value)
    const {firstName, lastName, username, password, role} = this.myForm.value
    if(firstName && lastName && username && password && role){
      const newUser: User = {firstName, lastName, username, password, role}
      this.authService.signup(newUser). subscribe({
        next: (response)=>{
          if(response){
            this.router.navigate(['/login'])
          }else{
            this.errorMessage = "Something went wrong, Please try again"
          }
        },
        error: (error)=>{
          this.errorMessage = "Something went wrong, Please try again"
        }
      })
    }
  }
}
