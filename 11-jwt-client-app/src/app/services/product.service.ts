import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Product } from '../models/product';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  apiUrl = environment.apiBaseURL+'/api/products'
  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getAllProducts(): Observable<Product[]>{
    const headers = new HttpHeaders({'Authorization': 'Bearer ' + this.tokenService.getToken()})
    return this.http.get<Product[]>(this.apiUrl, {headers})
  }
}
