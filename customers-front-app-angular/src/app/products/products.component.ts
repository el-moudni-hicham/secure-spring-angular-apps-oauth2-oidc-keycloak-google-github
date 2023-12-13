import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ProductsService} from "../services/products.service";
import {Product} from "../models/Product";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit{
  products! : Product[]
  constructor(private productsService : ProductsService) {

  }
  ngOnInit(): void {
    this.productsService.getProducts().subscribe({
      next : data =>{
        this.products = data
      },
      error : err =>{
        console.log(err)
      }
    })
  }

}
