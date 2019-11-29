import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = '/api/user';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/`);
  }

  public create(user: User) {
    return this.http.post<User>(`${this.baseUrl}/create`, user);
  }

  public delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/id/${id}`, {responseType: 'text'});
  }

  get(id: string): Observable<Object> {
    return this.http.get(`${this.baseUrl}/id/${id}`);
  }

  update(id: string, userData: any): Observable<Object> {
    return this.http.post(`${this.baseUrl}/${id}`, userData);
  }
}
