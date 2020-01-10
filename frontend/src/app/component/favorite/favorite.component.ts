import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FavoriteService} from "../../service/favorite.service";
import {Favorite} from "../../model/favorite";

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})
export class FavoriteComponent implements OnInit {

  favorite: Favorite = new Favorite();
  favorites: Favorite[];

  constructor(private router: Router, private favoriteService: FavoriteService) {
  }

  ngOnInit() {
    this.favoriteService.findAll(localStorage.getItem('id')).subscribe((data: Favorite[]) => {
      this.favorites = data;
    })
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
      this.favoriteService.findAll(localStorage.getItem('id')).subscribe(data => {
        this.favorites = data;
      });
    })
  }
}
