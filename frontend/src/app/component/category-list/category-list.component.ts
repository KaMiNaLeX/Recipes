import {Component, OnInit} from '@angular/core';
import {Category} from "../../model/category";
import {CategoryService} from "../../service/category.service";

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categories: Category[];
  category: Category = new Category();
  showDeleteMessage = false;

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.categoryService.findAll().subscribe(data => {
      this.categories = data;
    });
  }

  deleteCategory(id: number) {
    this.categoryService.delete(id)
      .subscribe(
        data => {
          console.log(data);
          this.showDeleteMessage = true;
          this.categoryService.findAll().subscribe(data => {
            this.categories = data;
          })
        },
        error => console.log(error));
  }

  view(id: number) {
    window.alert(id);
  }

}
