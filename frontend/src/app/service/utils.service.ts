import {Injectable} from '@angular/core';
import {AlertComponent} from "../component/alert/alert.component";
import {MatDialog} from "@angular/material/dialog";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {TranslateResponse} from "../model/translate-response";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private dialog: MatDialog, private http: HttpClient) {
  }

  alert(message: string) {
    const dialogRef = this.dialog.open(AlertComponent, {
      maxWidth: '30%',
      maxHeight: '30%',
      data: {message}
    });
  }

  public translate(lang: string, text: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      })
    };
    const formData = new HttpParams().set('text', text);

    return this.http.post(`https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200302T163844Z.db527d0652e7ab8f.c3d6fb52268044e26e03537290dfac5f99e4ee58&lang=${lang}`, formData, httpOptions);
  }
}
