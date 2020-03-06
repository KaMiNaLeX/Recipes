import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Category} from "../../../../model/category";
import {Router} from "@angular/router";
import {CategoryService} from "../../../../service/category.service";
import {UtilsService} from "../../../../service/utils.service";

@Component({
  selector: 'app-add-category-dialog',
  templateUrl: './add-category-dialog.component.html',
  styleUrls: ['./add-category-dialog.component.css']
})
export class AddCategoryDialogComponent implements OnInit {
  category: Category = new Category();
  returnCategory: Category = new Category();
  selectedFile: File = null;
  imgURL: any;

  constructor(
    public dialogRef: MatDialogRef<AddCategoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router, private categoryService: CategoryService, private utilsService: UtilsService) {
  }

  ngOnInit(): void {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addCategory() {
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.category.tagRu = "x";
      this.category.descriptionRu = "c";
      this.category.nameRu = "d";
      this.categoryService.create(this.category).subscribe(data => {
          this.returnCategory = data;
          if (this.returnCategory != null) {
            if (this.selectedFile != null) {
              this.returnCategory.imgSource = " ";
              this.categoryService.addPhoto4Category(this.returnCategory.id, this.selectedFile);
            }
            window.location.reload();
            this.utilsService.alert("category is created");
          } else {
            this.utilsService.alert("category exists");
          }
        }
      );
    } else this.utilsService.alert("fill all fields");
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
