import { TestBed } from '@angular/core/testing';

import { BeforeUnloadGuard } from './before-unload.guard';

describe('BeforeUnloadGuard', () => {
  let guard: BeforeUnloadGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(BeforeUnloadGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
