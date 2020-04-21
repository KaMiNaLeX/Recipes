import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Comment} from "../model/comment";

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = '/api/comments';
  }

  public findAll(page: number, size: number, sort: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/?page=${page}&size=${size}&sort=${sort}`);
  }

  public findByRecipeId(recipeId: string, page: number, size: number, sort: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/recipeId/${recipeId}?page=${page}&size=${size}&sort=${sort}`);
  }

  public create(comment: Comment) {
    return this.http.post<Comment>(`${this.baseUrl}/create`, comment);
  }

  public update(id: string, commentData: any): Observable<Comment> {
    return this.http.put<Comment>(`${this.baseUrl}/update/${id}`, commentData);
  }

  public delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, {responseType: 'text'});
  }

}
