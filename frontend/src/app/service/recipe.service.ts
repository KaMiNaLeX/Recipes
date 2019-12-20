import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Recipe} from "../model/recipe";
import {CreateRecipeDTO} from "../model/createRecipe/create-recipe-dto";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = '/api/recipe';
  }

  public getRecipesByCategoryName(categoryName: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/categoryName/${categoryName}`);
  }

  public createRecipe(recipe: CreateRecipeDTO) {
    return this.http.post<CreateRecipeDTO>(`${this.baseUrl}/createRecipe`, recipe);
  }

  public getById(id: string): Observable<CreateRecipeDTO> {
    return this.http.get<CreateRecipeDTO>(`${this.baseUrl}/id/${id}`);
  }

  public getByAuthorId(id: string): Observable<CreateRecipeDTO[]> {
    return this.http.get<CreateRecipeDTO[]>(`${this.baseUrl}/authorId/${id}`);
  }

  public delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, {responseType: 'text'});
  }

  public update(id: string, recipeData: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/updateRecipe/${id}`, recipeData);
  }

  public getByName(name: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/name/${name}`);
  }

  public getByAuthorName(name: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/authorName/${name}`);
  }
}
