import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {Ingredient} from "../../../../model/ingredient";
import {IngredientService} from "../../../../service/ingredient.service";
import {UtilsService} from "../../../../service/utils.service";

@Component({
  selector: 'app-delete-ingredient-dialog',
  templateUrl: './delete-ingredient-dialog.component.html',
  styleUrls: ['./delete-ingredient-dialog.component.css']
})
export class DeleteIngredientDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteIngredientDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Ingredient,
              private router: Router, private ingredientService: IngredientService,
              private utilsService: UtilsService) {
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  delete() {
    this.ingredientService.delete(this.data.id)
      .subscribe(
        data => {
          window.location.reload();
          if (data == false) {
            this.utilsService.alert("ingredient used");
          }
        },
        error => console.log(error));
  }
}
