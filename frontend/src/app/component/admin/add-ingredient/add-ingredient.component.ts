import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {IngredientService} from "../../../service/ingredient.service";
import {Ingredient} from "../../../model/ingredient";
import {TypeIngredient} from "../../../model/type-ingredient.enum";

@Component({
  selector: 'app-add-ingredient',
  templateUrl: './add-ingredient.component.html',
  styleUrls: ['./add-ingredient.component.css']
})
export class AddIngredientComponent implements OnInit {

  ingredients: Ingredient[] = [];
  ingredient: Ingredient = new Ingredient();
  firstDiv = true;
  secondDiv = false;
  thirdDiv = false;
  keys = [];

  constructor(private router: Router, private ingredientService: IngredientService) {
  }

  ngOnInit() {
    this.ingredientService.findAll().subscribe(data => {
      this.ingredients = data
    });
    let type = TypeIngredient;
    this.keys = Object.values(type);
  }

  view(id: string) {
    window.alert("ID:" + id);
  }

  add() {
    this.firstDiv = false;
    this.secondDiv = true;
  }

  addIngredient() {
    if (this.ingredient.name != null && this.ingredient.calories != null && this.ingredient.type != null) {
      this.ingredientService.create(this.ingredient).subscribe(data => {
          this.ingredientService.findAll().subscribe(data => {
            this.ingredients = data;
          })
        }
      );
      this.firstDiv = true;
      this.secondDiv = false;
      window.alert("Ingredient is created!");
    } else window.alert("Please, fill in all fields!");
  }

  edit(id: string) {
    this.firstDiv = false;
    this.thirdDiv = true;
    this.ingredientService.get(id).subscribe(data => {
      this.ingredient = data;
    });
  }

  editIngredient() {
    if (this.ingredient.name != null && this.ingredient.calories != null && this.ingredient.type != null) {
      this.ingredientService.update(this.ingredient.id, this.ingredient).subscribe(data => {
          this.ingredient = data;
          this.ingredientService.findAll().subscribe(data => {
            this.ingredients = data;
          })
        }
      );
      this.firstDiv = true;
      this.thirdDiv = false;
      window.alert("Category is updated!");
    } else window.alert("Please, fill in all fields!");
  }

}
