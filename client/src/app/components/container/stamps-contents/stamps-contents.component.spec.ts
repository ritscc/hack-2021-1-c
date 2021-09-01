import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampsContentsComponent } from './stamps-contents.component';

describe('StampsContentsComponent', () => {
  let component: StampsContentsComponent;
  let fixture: ComponentFixture<StampsContentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampsContentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StampsContentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
