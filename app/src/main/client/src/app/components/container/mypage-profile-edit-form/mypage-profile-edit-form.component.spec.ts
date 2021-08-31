import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MypageProfileEditFormComponent } from './mypage-profile-edit-form.component';

describe('MypageProfileEditFormComponent', () => {
  let component: MypageProfileEditFormComponent;
  let fixture: ComponentFixture<MypageProfileEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MypageProfileEditFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MypageProfileEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
