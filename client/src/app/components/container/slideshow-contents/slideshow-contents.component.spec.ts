import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlideshowContentsComponent } from './slideshow-contents.component';

describe('SlideshowContentsComponent', () => {
  let component: SlideshowContentsComponent;
  let fixture: ComponentFixture<SlideshowContentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SlideshowContentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SlideshowContentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
