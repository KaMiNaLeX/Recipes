import {Component, OnInit} from '@angular/core';
import {CreateRecipeDTO} from "../../model/createRecipe/create-recipe-dto";
import {RecipeService} from "../../service/recipe.service";
import {SharedService} from "../../service/shared.service";
import {FavoriteService} from "../../service/favorite.service";

@Component({
  selector: 'app-recipe-view',
  templateUrl: './recipe-view.component.html',
  styleUrls: ['./recipe-view.component.css']
})
export class RecipeViewComponent implements OnInit {
  createRecipeDTO: CreateRecipeDTO = new CreateRecipeDTO();
  ru: boolean;
  dataSource: any;
  dataSource2: any;
  displayedColumns: string[] = ['ingredient', 'amount', 'unit', 'note'];
  displayedColumns2: string[] = ['photo', 'description'];
  authenticated = false;

  constructor(private createRecipeService: RecipeService, private ss: SharedService, private favoriteService: FavoriteService) {
    if (localStorage.getItem('token') != undefined) {
      this.authenticated = true;
    }
  }

  ngOnInit() {
    this.createRecipeService.getById(sessionStorage.getItem('recipe')).subscribe(
      data => {
        this.createRecipeDTO = data;
        this.dataSource = this.createRecipeDTO.ingredientRecipeDTOList;
        this.dataSource2 = this.createRecipeDTO.cookingStepRecipeDTOList.reverse();
      });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  addToFavorite(id: string) {
    this.favoriteService.addToFavorite(id, this.authenticated);
  }
}
