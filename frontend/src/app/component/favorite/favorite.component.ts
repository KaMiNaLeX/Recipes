import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FavoriteService} from "../../service/favorite.service";
import {Favorite} from "../../model/favorite";
import {User} from "../../model/user";
import {RecipeService} from "../../service/recipe.service";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})
export class FavoriteComponent implements OnInit {
  ru: boolean;
  favorite: Favorite = new Favorite();
  favorites: Favorite[];

  constructor(private router: Router, private favoriteService: FavoriteService, private recipeService: RecipeService, private ss: SharedService) {
  }

  ngOnInit() {
    this.favoriteService.findAll(localStorage.getItem('id'),0, 10, "addedAt").subscribe((data: Favorite[]) => {
      this.favorites = data;
      for (let i = 0; i < this.favorites.length; i++) {
        this.recipeService.getAuthorName(this.favorites[i].recipeDTO.authorId).subscribe((data: User) => {
          this.favorites[i].recipeDTO.authorName = data.login;
        })
      }
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  view(id: string) {
    sessionStorage.setItem('recipe', id);
    this.router.navigate(['recipe-view']);
  }

  categories() {
    this.router.navigate(['category']);
  }

  deleteFromFavorite(id: string) {
    this.favoriteService.delete(id).subscribe(data => {
      this.favoriteService.findAll(localStorage.getItem('id'),0, 10, "addedAt").subscribe(data => {
        this.favorites = data;
      });
    })
  }
}
