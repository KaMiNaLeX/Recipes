import {Component, OnInit} from '@angular/core';
import {Category} from "../../model/category";
import {CategoryService} from "../../service/category.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categories: Category[];
  category: Category = new Category();
  showDeleteMessage = false;

  constructor(private categoryService: CategoryService, private router: Router) {
  }

  ngOnInit() {
    this.categoryService.findAll(0, 10, "name").subscribe(data => {
      this.categories = data;
    });
  }

  getRipesByCategoryName(name: string) {
    sessionStorage.removeItem('categoryName');
    sessionStorage.setItem('categoryName', name);
    this.router.navigate(['recipe-list']);
  }

  deleteCategory(id: number) {
    this.categoryService.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.showDeleteMessage = true;
          this.categoryService.findAll(0, 10, "name").subscribe(data => {
            this.categories = data;
          })
        },
        error => console.log(error));
  }

  view(id: number) {
    window.alert(id);
  }

}
