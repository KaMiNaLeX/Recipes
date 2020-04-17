import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Category} from "../../../../model/category";
import {Router} from "@angular/router";
import {CategoryService} from "../../../../service/category.service";
import {UtilsService} from "../../../../service/utils.service";
import {SharedService} from "../../../../service/shared.service";
import {TranslateResponse} from "../../../../model/translate-response";

@Component({
  selector: 'app-add-category-dialog',
  templateUrl: './add-category-dialog.component.html',
  styleUrls: ['./add-category-dialog.component.css']
})
export class AddCategoryDialogComponent implements OnInit {
  category: Category = new Category();
  returnCategory: Category = new Category();
  selectedFile: File = null;
  imgURL: any = null;
  ru: boolean;

  constructor(
    public dialogRef: MatDialogRef<AddCategoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router, private categoryService: CategoryService, private utilsService: UtilsService,
    private ss: SharedService) {
  }

  ngOnInit(): void {
    this.ru = (localStorage.getItem('lang') == 'ru');
    this.ss.getEmittedValue()
      .subscribe(item => {
        this.ru = item;
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addCategory() {
    if (this.category.name != null && this.category.description != null && this.category.tag != null) {
      this.category.imgSource = "img";
      this.categoryService.create(this.category).subscribe(data => {
          this.returnCategory = data;
          if (this.returnCategory != null) {
            if (this.selectedFile != null) {
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

  translateName() {
    if (this.ru != true) {
      if (this.category.name != null) {
        this.categoryService.getByName(this.category.name).subscribe(data => {
          if (data.id != null) {
            this.category.name = null;
            this.utilsService.alert("category exists");
          } else {
            this.utilsService.translate('en-ru', this.category.name).subscribe((data: TranslateResponse) => {
              this.category.nameRu = data.text[0];
            });
          }
        });
      }
    } else {
      if (this.category.nameRu != null) {
        this.categoryService.getByName(this.category.nameRu).subscribe(data => {
          if (data.id != null) {
            this.category.nameRu = null;
            this.utilsService.alert("category exists");
          } else {
            this.utilsService.translate('ru-en', this.category.nameRu).subscribe((data: TranslateResponse) => {
              this.category.name = data.text[0];
            });
          }
        });
      }
    }
  }

  translateDescription() {
    if (this.ru != true) {
      if (this.category.description != null) {
        this.utilsService.translate('en-ru', this.category.description).subscribe((data: TranslateResponse) => {
          this.category.descriptionRu = data.text[0];
        });
      }
    } else {
      if (this.category.descriptionRu != null) {
        this.utilsService.translate('ru-en', this.category.descriptionRu).subscribe((data: TranslateResponse) => {
          this.category.description = data.text[0];
        });
      }
    }
  }

  translateTag() {
    if (this.ru != true) {
      if (this.category.tag != null) {
        this.utilsService.translate('en-ru', this.category.tag).subscribe((data: TranslateResponse) => {
          this.category.tagRu = data.text[0];
        });
      }
    } else {
      if (this.category.tagRu != null) {
        this.utilsService.translate('ru-en', this.category.tagRu).subscribe((data: TranslateResponse) => {
          this.category.tag = data.text[0];
        });
      }
    }
  }
}
