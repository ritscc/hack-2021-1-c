import { Injectable } from '@angular/core';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root',
})
export class DownloadBlobService {
  download(blob: Blob, fileName: string) {
    saveAs(blob, fileName);
  }
}
