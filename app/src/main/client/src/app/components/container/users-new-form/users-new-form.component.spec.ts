import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersNewFormComponent } from './users-new-form.component';

describe('UsersNewFormComponent', () => {
  let component: UsersNewFormComponent;
  let fixture: ComponentFixture<UsersNewFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersNewFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersNewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
