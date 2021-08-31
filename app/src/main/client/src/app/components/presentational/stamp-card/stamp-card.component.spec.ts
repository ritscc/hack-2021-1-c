import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampCardComponent } from './stamp-card.component';

describe('StampCardComponent', () => {
  let component: StampCardComponent;
  let fixture: ComponentFixture<StampCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StampCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
