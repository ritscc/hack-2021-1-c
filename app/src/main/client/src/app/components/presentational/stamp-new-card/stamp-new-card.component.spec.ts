import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampNewCardComponent } from './stamp-new-card.component';

describe('StampNewCardComponent', () => {
  let component: StampNewCardComponent;
  let fixture: ComponentFixture<StampNewCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampNewCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StampNewCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
