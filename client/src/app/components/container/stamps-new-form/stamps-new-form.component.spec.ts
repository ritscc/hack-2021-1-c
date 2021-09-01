import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampsNewFormComponent } from './stamps-new-form.component';

describe('StampsNewFormComponent', () => {
  let component: StampsNewFormComponent;
  let fixture: ComponentFixture<StampsNewFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampsNewFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StampsNewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
