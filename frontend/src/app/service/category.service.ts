import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Category} from "../model/category";
import {CategoryRecipeDTO} from "../model/createRecipe/category-recipe-dto";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = '/api/category';
  }

  public findAll(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/`);
  }

  public findAllCategoriesDTO(): Observable<CategoryRecipeDTO[]> {
    return this.http.get<CategoryRecipeDTO[]>(`${this.baseUrl}/findAll`);
  }

  public create(category: Category) {
    return this.http.post<Category>(`${this.baseUrl}/create`, category);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, {responseType: 'text'});
  }

  get(id: number): Observable<Object> {
    return this.http.get(`${this.baseUrl}/id/${id}`);
  }

  update(id: number, userData: any): Observable<Object> {
    return this.http.post(`${this.baseUrl}/update/${id}`, userData);
  }
}
