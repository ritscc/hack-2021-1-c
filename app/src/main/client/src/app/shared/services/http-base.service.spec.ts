import { TestBed } from '@angular/core/testing';

import { HttpBaseService } from './http-base.service';

describe('HttpBaseService', () => {
  let service: HttpBaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpBaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
