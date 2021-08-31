import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersContentsComponent } from './users-contents.component';

describe('UsersContentsComponent', () => {
  let component: UsersContentsComponent;
  let fixture: ComponentFixture<UsersContentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersContentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersContentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
