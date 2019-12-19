import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeAuthorComponent } from './recipe-author.component';

describe('RecipeAuthorComponent', () => {
  let component: RecipeAuthorComponent;
  let fixture: ComponentFixture<RecipeAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecipeAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
