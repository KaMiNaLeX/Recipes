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

  public getRecipesByCategoryName(categoryName: string, userId: string, page: number, size: number, sort: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/categoryName/${categoryName}?page=${page}&size=${size}&sort=${sort}&userId=${userId}`);
  }

  public getRecipesByCategoryName2(categoryName: string, page: number, size: number, sort: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/categoryName/${categoryName}?page=${page}&size=${size}&sort=${sort}&userId=`);
  }

  public getCountAllRecipesInCategory(categoryName: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/countAllRecipesInCategory/${categoryName}`);
  }

  public getCountAllRecipesByIngredient(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/countAllRecipesByIngredient`);
  }

  public getCountAllRecipesByData(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/countAllRecipesByData`);
  }

  public getCountAllRecipesByName(categoryName: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/countAllRecipesByName/${categoryName}`);
  }

  public getCountAllRecipesByAuthorName(authorName: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/countAllRecipesByAuthorName/${authorName}`);
  }

  public getCountAllOwnRecipes(authorId: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/countAllOwnRecipes/${authorId}`);
  }

  public createRecipe(recipe: CreateRecipeDTO) {
    return this.http.post<CreateRecipeDTO>(`${this.baseUrl}/createRecipe`, recipe);
  }

  public getById(id: string): Observable<CreateRecipeDTO> {
    return this.http.get<CreateRecipeDTO>(`${this.baseUrl}/id/${id}`);
  }

  public getByAuthorId(id: string, page: number, size: number, sort: string): Observable<CreateRecipeDTO[]> {
    return this.http.get<CreateRecipeDTO[]>(`${this.baseUrl}/authorId/${id}?page=${page}&size=${size}&sort=${sort}`);
  }

  public delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, {responseType: 'text'});
  }

  public update(id: string, recipeData: any): Observable<CreateRecipeDTO> {
    return this.http.put<CreateRecipeDTO>(`${this.baseUrl}/updateRecipe/${id}`, recipeData);
  }

  public getByName(name: string, page: number, size: number, sort: string, userId: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/name/${name}?page=${page}&size=${size}&sort=${sort}&userId=${userId}`);
  }

  public getByName2(name: string, page: number, size: number, sort: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/name/${name}?page=${page}&size=${size}&sort=${sort}&userId=`);
  }

  public getByNameAndAuthor(name: string, id: string): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.baseUrl}/nameAndAuthor/${name}/${id}`);
  }

  public getByAuthorName(name: string, page: number, size: number, sort: string, userId: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/authorName/${name}?page=${page}&size=${size}&sort=${sort}&userId=${userId}`);
  }

  public getByAuthorName2(name: string, page: number, size: number, sort: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.baseUrl}/authorName/${name}?page=${page}&size=${size}&sort=${sort}&userId=`);
  }

  public findAllByIngredients(ingredientsList: IngredientNameListDTO, page: number, size: number, sort: string, userId: string) {
    return this.http.post<Recipe[]>(`${this.baseUrl}/ingredientName?page=${page}&size=${size}&sort=${sort}&userId=${userId}`, ingredientsList);
  }

  public findAllByIngredients2(ingredientsList: IngredientNameListDTO, page: number, size: number, sort: string) {
    return this.http.post<Recipe[]>(`${this.baseUrl}/ingredientName?page=${page}&size=${size}&sort=${sort}&userId=`, ingredientsList);
  }

  public findAllByData(recipeData: RecipeDataDTO, page: number, size: number, sort: string, userId: string) {
    return this.http.post<Recipe[]>(`${this.baseUrl}/data?page=${page}&size=${size}&sort=${sort}&userId=${userId}`, recipeData);
  }

  public findAllByData2(recipeData: RecipeDataDTO, page: number, size: number, sort: string) {
    return this.http.post<Recipe[]>(`${this.baseUrl}/data?page=${page}&size=${size}&sort=${sort}&userId=`, recipeData);
  }

  public addPhoto4Step(stepId: string, file: File) {
    const data = new FormData();
    data.append('file', file);
    console.log(data);
    fetch(`${this.baseUrl}/addPhoto4Step/${stepId}`, {
      method: 'POST',
      body: data
    }).then(r => {

    }).catch(r => {

    });
  }

  public addPhoto4Recipe(recipeId: string, file: File) {
    const data = new FormData();
    data.append('file', file);
    fetch(`${this.baseUrl}/addPhoto4Recipe/${recipeId}`, {
      method: 'POST',
      body: data
    }).then(r => {

    });
  }
}
