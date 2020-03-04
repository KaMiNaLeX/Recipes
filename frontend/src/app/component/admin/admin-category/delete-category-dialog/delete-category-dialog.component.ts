import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Category} from "../../../../model/category";
import {Router} from "@angular/router";
import {CategoryService} from "../../../../service/category.service";

@Component({
  selector: 'app-delete-category-dialog',
  templateUrl: './delete-category-dialog.component.html',
  styleUrls: ['./delete-category-dialog.component.css']
})
export class DeleteCategoryDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteCategoryDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Category,
              private router: Router, private categoryService: CategoryService) {
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  delete() {
    this.categoryService.delete(this.data.id)
      .subscribe(
        data => {
          window.location.reload();
        },
        error => console.log(error));
    window.alert("Category is deleted!");
  }

}
