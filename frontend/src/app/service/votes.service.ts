import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Vote} from "../model/vote";

@Injectable({
  providedIn: 'root'
})
export class VotesService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = '/api/vote';
  }

  public createVote(vote: Vote) {
    return this.http.post<Vote>(`${this.baseUrl}/create`, vote);
  }

}
