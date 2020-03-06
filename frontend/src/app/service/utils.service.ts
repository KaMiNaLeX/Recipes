import {Injectable} from '@angular/core';
import {AlertComponent} from "../component/alert/alert.component";
import {MatDialog} from "@angular/material/dialog";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private dialog: MatDialog) {
  }

  alert(message: string) {
    const dialogRef = this.dialog.open(AlertComponent, {
      maxWidth: '30%',
      maxHeight: '30%',
      data: {message}
    });
  }
}
