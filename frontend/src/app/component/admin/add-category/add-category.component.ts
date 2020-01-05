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
  category: Category = new Category();
  firstDiv = true;
  secondDiv = false;
  thirdDiv = false;

  selectedFile: File = null;
  imgURL: any;

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
    this.firstDiv = false;
    this.secondDiv = true;
  }

  addCategory() {
    this.category.imgSource = " ";
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.categoryService.create(this.category).subscribe(data => {
          this.category = data;
          this.categoryService.addPhoto4Category(this.category.id, this.selectedFile);
          this.categoryService.findAll().subscribe(data => {
            this.categories = data;
          })
        }
      );
      this.firstDiv = true;
      this.secondDiv = false;
      window.alert("Category is created!");
      window.location.reload();
    } else window.alert("Please, fill in all fields!");
  }

  edit(id: number) {
    this.firstDiv = false;
    this.thirdDiv = true;
    this.categoryService.get(id).subscribe(data => {
      this.category = data;
    })
  }

  editCategory() {
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.categoryService.update(this.category.id, this.category).subscribe(data => {
          this.category = data;
          this.categoryService.addPhoto4Category(this.category.id, this.selectedFile);
          this.categoryService.findAll().subscribe(data => {
            this.categories = data;
          })
        }
      );
      this.firstDiv = true;
      this.thirdDiv = false;
      window.location.reload();
      window.alert("Category is updated!");
    } else window.alert("Please, fill in all fields!");
  }

  delete(id: number) {
    this.categoryService.delete(id)
      .subscribe(
        data => {
          this.categoryService.findAll().subscribe(data => {
            this.categories = data;
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

}
