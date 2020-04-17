import {Component, Inject, OnInit} from '@angular/core';
import {Ingredient} from "../../../../model/ingredient";
import {IngredientService} from "../../../../service/ingredient.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {SharedService} from "../../../../service/shared.service";
import {TypeIngredient} from "../../../../model/Enum/type-ingredient.enum";
import {TypeIngredientRu} from "../../../../model/Enum/type-ingredient-ru.enum";
import {UtilsService} from "../../../../service/utils.service";
import {TranslateResponse} from "../../../../model/translate-response";

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
  type = [];
  typeRu = [];
  primaryName: String;
  primaryNameRu: String;

  constructor(private ingredientService: IngredientService, public dialogRef: MatDialogRef<EditIngredientDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Ingredient,
              private router: Router, private ss: SharedService, private utilsService: UtilsService) {
  }

  ngOnInit(): void {
    let type = TypeIngredient;
    this.type = Object.values(type);
    let typeRu = TypeIngredientRu;
    this.typeRu = Object.values(typeRu);

    if (this.ru != true) {
      let type = TypeIngredient;
      this.keys = Object.values(type);
    } else {
      let type = TypeIngredientRu;
      this.keys = Object.values(type);
    }

    this.ingredientService.get(this.data.id).subscribe(data => {
      this.ingredient = data;
      this.primaryName = data.name;
      this.primaryNameRu = data.nameRu;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
        if (this.ru != true) {
          let type = TypeIngredient;
          this.keys = Object.values(type);
        } else {
          let type = TypeIngredientRu;
          this.keys = Object.values(type);
        }
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  editIngredient() {
    if (this.ru != true) {
      let i = this.type.indexOf(this.ingredient.type);
      this.ingredient.typeRu = this.typeRu[i];
    } else {
      let i = this.typeRu.indexOf(this.ingredient.typeRu);
      this.ingredient.type = this.type[i];
    }

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

  translateName() {
    if (this.ru != true) {
      if (this.ingredient.name != null && this.primaryName != this.ingredient.name) {
        this.ingredientService.getByName(this.ingredient.name).subscribe(data => {
          if (data.id != null) {
            this.ingredient.name = null;
            this.utilsService.alert("ingredient exists");
          } else {
            this.utilsService.translate('en-ru', this.ingredient.name).subscribe((data: TranslateResponse) => {
              this.ingredient.nameRu = data.text[0];
            });
          }
        });
      }
    } else {
      if (this.ingredient.nameRu != null && this.primaryNameRu != this.ingredient.nameRu) {
        this.ingredientService.getByName(this.ingredient.nameRu).subscribe(data => {
          if (data.id != null) {
            this.ingredient.nameRu = null;
            this.utilsService.alert("ingredient exists");
          } else {
            this.utilsService.translate('ru-en', this.ingredient.nameRu).subscribe((data: TranslateResponse) => {
              this.ingredient.name = data.text[0];
            });
          }
        });
      }
    }
  }

  translateDescription() {
    if (this.ru != true) {
      if (this.ingredient.description != null) {
        this.utilsService.translate('en-ru', this.ingredient.description).subscribe((data: TranslateResponse) => {
          this.ingredient.descriptionRu = data.text[0];
        });
      }
    } else {
      if (this.ingredient.descriptionRu != null) {
        this.utilsService.translate('ru-en', this.ingredient.descriptionRu).subscribe((data: TranslateResponse) => {
          this.ingredient.description = data.text[0];
        });
      }
    }
  }
}
