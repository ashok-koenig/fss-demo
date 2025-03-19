import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Book } from '../models/book';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  apiUrl = environment.apiBaseURL+'/api/books'
  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getAllBooks(): Observable<Book[]>{
    const headers = new HttpHeaders({'Authorization': 'Bearer ' + this.tokenService.getToken()})
    return this.http.get<Book[]>(this.apiUrl, {headers})
  }
}
