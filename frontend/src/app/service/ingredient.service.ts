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

  public create(ingredient: Ingredient) {
    return this.http.post<Ingredient>(`${this.baseUrl}/create`, ingredient);
  }

  public get(id: string): Observable<Ingredient> {
    return this.http.get<Ingredient>(`${this.baseUrl}/${id}`);
  }

  public update(id: string, userData: Ingredient): Observable<Ingredient> {
    return this.http.put<Ingredient>(`${this.baseUrl}/update/${id}`, userData);
  }

  public delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(`${this.baseUrl}/delete/${id}`);
  }
}
