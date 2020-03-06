import {Component, OnInit} from '@angular/core';
import {RecipeService} from "../../service/recipe.service";
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {Router} from "@angular/router";
import {SharedService} from "../../service/shared.service";
import {UtilsService} from "../../service/utils.service";

@Component({
  selector: 'app-recipe-author',
  templateUrl: './recipe-author.component.html',
  styleUrls: ['./recipe-author.component.css']
})
export class RecipeAuthorComponent implements OnInit {
  recipes: CreateRecipeDTO[] = [];
  recipe: CreateRecipeDTO = new CreateRecipeDTO();
  ru: boolean;

  constructor(private router: Router, private recipeService: RecipeService, private ss: SharedService,
              private utilsService: UtilsService) {
  }

  ngOnInit() {
    this.recipeService.getByAuthorId(localStorage.getItem('id'), 0, 10, "name").subscribe(data => this.recipes = data);
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  edit(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-edit']);
  }

  addRecipe() {
    this.router.navigate(['recipe-add']);
  }

  delete(id: string) {
    this.recipeService.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.recipeService.getByAuthorId(localStorage.getItem('id'), 0, 10, "name").subscribe(data => {
            this.recipes = data;
          })
        },
        error => console.log(error));
    this.utilsService.alert("recipe deleted");
  }
}
