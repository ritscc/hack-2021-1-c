import { TestBed } from '@angular/core/testing';

// shared module
import { SharedModule } from 'src/app/shared/shared.module';

import { LoginedGuard } from './logined.guard';

describe('LoginedGuard', () => {
  let guard: LoginedGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [SharedModule] });
    guard = TestBed.inject(LoginedGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
