import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  userRole: string =''
  isLoggedIn: boolean = false
  constructor(private authService: AuthService, private router: Router, private tokenService: TokenService){
      tokenService.isLoggedIn.subscribe((value)=>{
        this.isLoggedIn = value
      })
      tokenService.userRole.subscribe((value)=>{
        this.userRole = value
      })
  }

  handleLogout(){
    this.authService.logout();
    this.router.navigate(['/login'])
  }

}
