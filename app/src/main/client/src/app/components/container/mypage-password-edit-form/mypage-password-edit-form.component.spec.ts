import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MypagePasswordEditFormComponent } from './mypage-password-edit-form.component';

describe('MypagePasswordEditFormComponent', () => {
  let component: MypagePasswordEditFormComponent;
  let fixture: ComponentFixture<MypagePasswordEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MypagePasswordEditFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MypagePasswordEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
