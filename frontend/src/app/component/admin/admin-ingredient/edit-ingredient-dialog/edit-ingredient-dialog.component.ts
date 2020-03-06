import {Component, Inject, OnInit} from '@angular/core';
import {Ingredient} from "../../../../model/ingredient";
import {IngredientService} from "../../../../service/ingredient.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {SharedService} from "../../../../service/shared.service";
import {TypeIngredient} from "../../../../model/type-ingredient.enum";
import {TypeIngredientRu} from "../../../../model/type-ingredient-ru.enum";
import {UtilsService} from "../../../../service/utils.service";

@Component({
  selector: 'app-edit-ingredient-dialog',
  templateUrl: './edit-ingredient-dialog.component.html',
  styleUrls: ['./edit-ingredient-dialog.component.css']
})
export class EditIngredientDialogComponent implements OnInit {
  ru: boolean;
  ingredient: Ingredient = new Ingredient();
  returnIngredient: Ingredient = new Ingredient();
  keys = [];

  constructor(private ingredientService: IngredientService, public dialogRef: MatDialogRef<EditIngredientDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Ingredient,
              private router: Router, private ss: SharedService, private utilsService: UtilsService) {
  }

  ngOnInit(): void {
    this.ingredientService.get(this.data.id).subscribe(data => {
      this.ingredient = data;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
      });
    if (this.ru != true) {
      let type = TypeIngredient;
      this.keys = Object.values(type);
    } else {
      let type = TypeIngredientRu;
      this.keys = Object.values(type);
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  editIngredient() {
    if (this.ingredient.name != null && this.ingredient.calories != null && this.ingredient.type != null) {
      this.ingredientService.update(this.ingredient.id, this.ingredient).subscribe(data => {
          this.returnIngredient = data;
          if (this.returnIngredient != null) {
            window.location.reload();
            this.utilsService.alert("ingredient is updated");
          } else {
            this.utilsService.alert("ingredient exists");
          }
        }
      );
    } else this.utilsService.alert("fill all fields");
  }
}
