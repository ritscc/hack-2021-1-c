import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersEditFormComponent } from './users-edit-form.component';

describe('UsersEditFormComponent', () => {
  let component: UsersEditFormComponent;
  let fixture: ComponentFixture<UsersEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersEditFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
