import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../models/Product";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private baseURL : string = "http://localhost:8082"
  constructor(private http : HttpClient) { }

  getProducts() : Observable<Product[]>{
    return this.http.get<Product[]>(`${this.baseURL}/products`);
  }

}
