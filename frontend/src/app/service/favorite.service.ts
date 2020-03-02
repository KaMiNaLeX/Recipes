import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Favorite} from "../model/favorite";
import {CreateFavorite} from "../model/create-favorite";

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
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
}
