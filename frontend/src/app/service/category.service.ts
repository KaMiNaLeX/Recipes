import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
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

  public get(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.baseUrl}/id/${id}`);
  }

  public update(id: number, userData: Category): Observable<Category> {
    return this.http.put<Category>(`${this.baseUrl}/update/${id}`, userData);
  }

  public addPhoto4Category(stepId: number, file: File) {
    const data = new FormData();
    data.append('file', file);
    fetch(`${this.baseUrl}/addPhoto4Category/${stepId}`, {
      method: 'POST',
      body: data
    }).then(r => {

    }).catch(r => {
      alert(r);
    });
  }
}
