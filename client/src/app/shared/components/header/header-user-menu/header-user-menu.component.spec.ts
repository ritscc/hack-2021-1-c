import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderUserMenuComponent } from './header-user-menu.component';

describe('HeaderUserMenuComponent', () => {
  let component: HeaderUserMenuComponent;
  let fixture: ComponentFixture<HeaderUserMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderUserMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderUserMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
