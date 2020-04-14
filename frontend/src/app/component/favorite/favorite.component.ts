import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FavoriteService} from "../../service/favorite.service";
import {Favorite} from "../../model/favorite";
import {RecipeService} from "../../service/recipe.service";
import {SharedService} from "../../service/shared.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})
export class FavoriteComponent implements OnInit {
  ru: boolean;
  favorite: Favorite = new Favorite();
  favorites: Favorite[] = [];
  // MatPaginator Inputs
  length: number;
  pageSize = 8;
  pageSizeOptions: number[] = [8, 32, 64];
  // MatPaginator Output
  pageEvent: PageEvent;

  constructor(private router: Router, private favoriteService: FavoriteService, private recipeService: RecipeService, private ss: SharedService) {
  }

  ngOnInit() {
    this.favoriteService.getCountAllFavoritesRecipes(localStorage.getItem('id')).subscribe(data => {
      this.length = data;
    });
    this.favoriteService.findAll(localStorage.getItem('id'), 0, this.pageSize, "addedAt").subscribe((data: Favorite[]) => {
      this.favorites = data;
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
      if (this.pageEvent != undefined) {
        this.favoriteService.findAll(localStorage.getItem('id'), this.pageEvent.pageIndex, this.pageEvent.pageSize, "addedAt").subscribe(data => {
          this.favorites = data;
        });
      } else {
        this.favoriteService.findAll(localStorage.getItem('id'), 0, this.pageSize, "addedAt").subscribe(data => {
          this.favorites = data;
        });
      }
      this.favoriteService.getCountAllFavoritesRecipes(localStorage.getItem('id')).subscribe(data => {
        this.length = data;
      });
    });
  }

  getServerData(event?: PageEvent) {
    this.favoriteService.findAll(localStorage.getItem('id'), event.pageIndex, event.pageSize, "addedAt").subscribe(
      response => {
        this.favorites = response;
      }
    );
    return event;
  }
}
