import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CategoryService} from "../../../service/category.service";
import {Category} from "../../../model/category";
import {SharedService} from "../../../service/shared.service";

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {
  ru: boolean;
  allCategories: Category[] = [];
  category: Category = new Category();
  returnCategory: Category = new Category();
  firstDiv = true;
  secondDiv = false;
  thirdDiv = false;

  selectedFile: File = null;
  imgURL: any;

  selectedFile2: File = null;
  imgURL2: any;

  constructor(private router: Router, private categoryService: CategoryService, private ss: SharedService) {
  }

  ngOnInit() {
    this.categoryService.findAll(0, 10, "name").subscribe(data => {
      this.allCategories = data;
    });
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => this.ru = item);
  }

  toAdmin() {
    this.router.navigate(['admin']);
  }

  toView() {
    this.firstDiv = true;
    this.secondDiv = false;
    this.thirdDiv = false;
  }

  view(id: number) {
    window.alert("ID:" + id);
  }

  add() {
    this.firstDiv = false;
    this.secondDiv = true;
  }

  addCategory() {
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.categoryService.create(this.category).subscribe(data => {
          this.returnCategory = data;
          if (this.returnCategory != null) {
            if (this.selectedFile != null) {
              this.returnCategory.imgSource = " ";
              this.categoryService.addPhoto4Category(this.returnCategory.id, this.selectedFile);
            }
            window.alert("Category is created!");
            this.categoryService.findAll(0, 10, "name").subscribe(data => {
              this.allCategories = data;
              this.firstDiv = true;
              this.secondDiv = false;
            })
          } else {
            window.alert("The category with this name is already exists!");
          }
        }
      );
    } else window.alert("Please, fill in all fields!");
  }

  edit(id: number) {
    this.firstDiv = false;
    this.thirdDiv = true;
    this.categoryService.get(id).subscribe(data => {
      this.category = data;
    });
  }

  editCategory() {
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.categoryService.update(this.category.id, this.category).subscribe(data => {
          this.returnCategory = data;
          if (this.returnCategory != null) {
            if (this.imgURL2 != null) {
              this.categoryService.addPhoto4Category(this.returnCategory.id, this.selectedFile2);
            }
            window.alert("Category is updated!");
            this.categoryService.findAll(0, 10, "name").subscribe(data => {
              this.allCategories = data;
              this.firstDiv = true;
              this.thirdDiv = false;
            })
          } else {
            window.alert("The category with this name is already exists!");
          }
        }
      );
    } else window.alert("Please, fill in all fields!");
  }

  delete(id: number) {
    this.categoryService.delete(id)
      .subscribe(
        data => {
          this.categoryService.findAll(0, 10, "name").subscribe(data => {
            this.allCategories = data;
          })
        },
        error => console.log(error));
    window.alert("Category is deleted!");
  }

  handleFileInput(event) {
    console.log(event);
    this.selectedFile = event.target.files[0];
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL = reader.result;
    }
  }

  handleFileInput2(event) {
    console.log(event);
    this.selectedFile2 = event.target.files[0];
    // Below part is used to display the selected image
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = (event2) => {
      this.imgURL2 = reader.result;
    }
  }
}
