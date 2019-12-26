import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CategoryService} from "../../../service/category.service";
import {Category} from "../../../model/category";

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  categories: Category[] = [];

  constructor(private router: Router, private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.categoryService.findAll().subscribe(data => {
      this.categories = data
    });
  }

  view(id: number) {
    window.alert("ID:" + id);
  }

  add() {

  }

  edit() {

  }

  delete(id: number) {
    window.alert("Category " + id + "deleted successful")
  }

}
