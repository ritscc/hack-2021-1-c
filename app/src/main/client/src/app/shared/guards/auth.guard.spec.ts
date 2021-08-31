import { TestBed } from '@angular/core/testing';

// shared module
import { SharedModule } from 'src/app/shared/shared.module';

import { AuthGuard } from './auth.guard';

describe('AuthGuard', () => {
  let guard: AuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [SharedModule] });
    guard = TestBed.inject(AuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
