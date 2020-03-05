import {Component, Inject, OnInit} from '@angular/core';
import {Ingredient} from "../../../../model/ingredient";
import {IngredientService} from "../../../../service/ingredient.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {TypeIngredient} from "../../../../model/type-ingredient.enum";
import {TypeIngredientRu} from "../../../../model/type-ingredient-ru.enum";
import {SharedService} from "../../../../service/shared.service";

@Component({
  selector: 'app-add-ingredient-dialog',
  templateUrl: './add-ingredient-dialog.component.html',
  styleUrls: ['./add-ingredient-dialog.component.css']
})
export class AddIngredientDialogComponent implements OnInit {
  ru: boolean;
  ingredient: Ingredient = new Ingredient();
  returnIngredient: Ingredient = new Ingredient();
  keys = [];

  constructor(private ingredientService: IngredientService, public dialogRef: MatDialogRef<AddIngredientDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private router: Router, private ss: SharedService) {
  }

  ngOnInit(): void {
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

  addIngredient() {
    if (this.ingredient.name != null && this.ingredient.calories != null && this.ingredient.type != null) {
      this.ingredient.nameRu = "test";
      this.ingredient.typeRu = "ОВОЩ";
      this.ingredient.descriptionRu = "";
      this.ingredientService.create(this.ingredient).subscribe(data => {
          this.returnIngredient = data;
          if (this.returnIngredient != null) {
            window.location.reload();
            window.alert("Ingredient is created!");
          } else {
            window.alert("The ingredient with this name already exists!");
          }
        }
      );
    } else window.alert("Please, fill in all fields!");
  }
}
