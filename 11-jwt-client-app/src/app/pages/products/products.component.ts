import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products',
  imports: [CommonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {

  products: Product[] = []
  constructor(private productService: ProductService){
    this.loadProducts()
  }

  loadProducts(){
    this.productService.getAllProducts().subscribe((data)=>{
      console.log(data);
      this.products = data
    })
  }
}
