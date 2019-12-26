import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {IngredientService} from "../../../service/ingredient.service";
import {Ingredient} from "../../../model/ingredient";

@Component({
  selector: 'app-add-ingredient',
  templateUrl: './add-ingredient.component.html',
  styleUrls: ['./add-ingredient.component.css']
})
export class AddIngredientComponent implements OnInit {

  ingredients: Ingredient[] = [];

  constructor(private router: Router, private ingredientService: IngredientService) {
  }

  ngOnInit() {
    this.ingredientService.findAll().subscribe(data => {
      this.ingredients = data
    });
  }

}
