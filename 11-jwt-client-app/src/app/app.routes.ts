import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ProductsComponent } from './pages/products/products.component';
import { UnauthorizedComponent } from './pages/unauthorized/unauthorized.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { authGuard } from './guards/auth.guard';
import { BooksComponent } from './pages/books/books.component';
import { unsavedGuard } from './guards/unsaved.guard';
// import { PaymentsComponent } from './pages/payments/payments.component';

export const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
    {path: 'signup', component: SignupComponent, canDeactivate: [unsavedGuard]},
    {path: 'products', component: ProductsComponent, canActivate:[authGuard], data: {roles: ['ADMIN', 'USER']}},
    {path: 'books', component: BooksComponent, canActivate:[authGuard], data: {roles: ['ADMIN']}},
    // {path: 'payment', component: PaymentsComponent},
    {path: 'payment', loadComponent: ()=> import('./pages/payments/payments.component').then((c)=> c.PaymentsComponent) },
    {path: 'unauthorized', component: UnauthorizedComponent},
    {path: '**', component: PageNotFoundComponent}
];
