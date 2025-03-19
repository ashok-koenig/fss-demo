import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

export const authGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService)
  const router = inject(Router)

  tokenService.isLoggedIn.subscribe((value)=>{
    if(!value){
      router.navigate(['/unauthorized'])
    }
  })

  const allowedRoles: string[] = route.data['roles'];
  if(!allowedRoles.includes(tokenService.getRole())){
    router.navigate(['/unauthorized'])
  }

  return true;
};
