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

  public findAll(page: number, size: number, sort: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/?page=${page}&size=${size}&sort=${sort}`);
  }

  public create(user: User) {
    return this.http.post<User>(`${this.baseUrl}/create`, user);
  }

  public delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/id/${id}`, {responseType: 'text'});
  }

  get(id: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/id/${id}`);
  }

  getByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/email/${email}`);
  }

  getByLogin(login: string): Observable<User> {
    return this.http.get <User>(`${this.baseUrl}/login/${login}`);
  }

  update(id: string, userData: any): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, userData);
  }

  checkPass(id: string, userData: string): Observable<Boolean> {
    return this.http.put<Boolean>(`${this.baseUrl}/checkPass/${id}`, userData);
  }

  savePass(id: string, userData: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/savePass/${id}`, userData);
  }
}
