import { Component } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-books',
  imports: [],
  templateUrl: './books.component.html',
  styleUrl: './books.component.css'
})
export class BooksComponent {
  books: Book[] = []

  constructor(private bookServie: BookService){
    this.loadBooks()
  }

  loadBooks(){
    this.bookServie.getAllBooks().subscribe((data)=>{
      this.books = data;
    })
  }
}
