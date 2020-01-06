import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Recipe} from "../model/recipe";
import {CreateRecipeDTO} from "../model/createRecipe/create-recipe-dto";
import {IngredientNameListDTO} from "../model/findByIngredients/ingredient-name-list-dto";
import {RecipeDataDTO} from "../model/findByData/recipe-data-dto";

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

  public update(id: string, recipeData: any): Observable<CreateRecipeDTO> {
    return this.http.put<CreateRecipeDTO>(`${this.baseUrl}/updateRecipe/${id}`, recipeData);
  }

  public getByName(name: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/name/${name}`);
  }

  public getByAuthorName(name: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/authorName/${name}`);
  }

  public findAllByIngredients(ingredientsList: IngredientNameListDTO) {
    return this.http.post<Recipe[]>(`${this.baseUrl}/ingredientName`, ingredientsList);
  }

  public findAllByData(recipeData: RecipeDataDTO) {
    return this.http.post<Recipe[]>(`${this.baseUrl}/data`, recipeData);
  }

  public addPhoto4Step(stepId: string, file: File) {
    const data = new FormData();
    data.append('file', file);
    fetch(`${this.baseUrl}/addPhoto4Step/${stepId}`, {
      method: 'POST',
      body: data
    }).then(r => {

    }).catch(r => {
      alert(r);
    });
  }

  public addPhoto4Recipe(recipeId: string, file: File) {
    const data = new FormData();
    data.append('file', file);
    fetch(`${this.baseUrl}/addPhoto4Recipe/${recipeId}`, {
      method: 'POST',
      body: data
    }).then(r => {

    }).catch(r => {
      alert(r);
    });
  }
}
