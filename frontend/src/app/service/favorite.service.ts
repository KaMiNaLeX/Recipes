import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Favorite} from "../model/favorite";
import {CreateFavorite} from "../model/create-favorite";
import {UtilsService} from "./utils.service";

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  private baseUrl: string;

  constructor(private http: HttpClient, private utilsService: UtilsService) {
    this.baseUrl = '/api/favorite';
  }

  public findAll(userId: string, page: number, size: number, sort: string) {
    return this.http.get<Favorite[]>(`${this.baseUrl}/${userId}?page=${page}&size=${size}&sort=${sort}`);
  }

  public create(favorite: CreateFavorite) {
    return this.http.post<Favorite>(`${this.baseUrl}/create`, favorite);
  }

  public delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, {responseType: 'text'});
  }

  addToFavorite(id: string, authenticated: boolean) {
    let createFavorite = new CreateFavorite();
    let favorite = new Favorite();
    if (authenticated != false) {
      createFavorite.recipeId = id;
      createFavorite.userId = localStorage.getItem('id');
      this.create(createFavorite).subscribe(data => {
        favorite = data;
        if (favorite == null) {
          this.utilsService.alert("recipe in favorites");
        } else {
          this.utilsService.alert("recipe add to favorites");
        }
      })
    } else this.utilsService.alert("you need to authenticated");
  }
}
