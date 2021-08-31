import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampsComponent } from './stamps.component';

describe('StampsComponent', () => {
  let component: StampsComponent;
  let fixture: ComponentFixture<StampsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StampsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
