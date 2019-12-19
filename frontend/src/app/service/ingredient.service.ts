import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Ingredient} from "../model/ingredient";

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = '/api/ingredient';
  }

  public findAll(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${this.baseUrl}/`);
  }
}
