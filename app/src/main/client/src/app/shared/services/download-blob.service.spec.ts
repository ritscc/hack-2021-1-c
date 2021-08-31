import { TestBed } from '@angular/core/testing';

import { DownloadBlobService } from './download-blob.service';

describe('DownloadBlobService', () => {
  let service: DownloadBlobService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DownloadBlobService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
