import {Component, Inject, OnInit} from '@angular/core';
import {Category} from "../../../../model/category";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {CategoryService} from "../../../../service/category.service";
import {UtilsService} from "../../../../service/utils.service";

@Component({
  selector: 'app-edit-category-dialog',
  templateUrl: './edit-category-dialog.component.html',
  styleUrls: ['./edit-category-dialog.component.css']
})
export class EditCategoryDialogComponent implements OnInit {
  category: Category = new Category();
  returnCategory: Category = new Category();
  selectedFile: File = null;
  imgURL: any;

  constructor(public dialogRef: MatDialogRef<EditCategoryDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Category,
              private router: Router, private categoryService: CategoryService, private utilsService: UtilsService) {
  }

  ngOnInit(): void {
    this.categoryService.get(this.data.id).subscribe(data => {
      this.category = data;
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  editCategory() {
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.categoryService.update(this.category.id, this.category).subscribe(data => {
          this.returnCategory = data;
          if (this.returnCategory != null) {
            if (this.imgURL != null) {
              this.categoryService.addPhoto4Category(this.returnCategory.id, this.selectedFile);
            }
            window.location.reload();
            this.utilsService.alert("category is updated");
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
