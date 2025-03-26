import { Component } from '@angular/core';
import { ProductsService } from '../services/products.service';

@Component({
  selector: 'app-products',
  imports: [],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {
  products: any[]=[]
  constructor(private productsServcie: ProductsService){
    productsServcie.getAllProducts().subscribe((data)=>{
      console.log(data)
      this.products = data
    })
  }
}
