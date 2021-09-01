import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampsNewComponent } from './stamps-new.component';

describe('StampsNewComponent', () => {
  let component: StampsNewComponent;
  let fixture: ComponentFixture<StampsNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampsNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StampsNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
