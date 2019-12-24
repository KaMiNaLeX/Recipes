import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Ingredient} from "../model/ingredient";
import {TypeIngredient} from "../model/type-ingredient.enum";

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

  public findAllByType(type: TypeIngredient): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${this.baseUrl}/type/${type}`);
  }
}