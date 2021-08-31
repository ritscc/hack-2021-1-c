import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { HttpBaseService } from 'src/app/shared/services/http-base.service';
import { StampModel, StampsModel } from 'src/app/model/stamp-model';
import { StampCreateRequest } from 'src/app/request/stamp-request';

@Injectable({
  providedIn: 'root',
})
export class StampService {
  stamps: BehaviorSubject<StampModel[]> = new BehaviorSubject<StampModel[]>([]);

  constructor(private httpBaseService: HttpBaseService) {}

  getStamps(): Observable<StampModel[]> {
    if (Object.keys(this.stamps.getValue()).length === 0) {
      this.fetchStamps();
    }

    return this.stamps;
  }

  fetchStamps(): void {
    this.httpBaseService.getRequest<any>(`${environment.API_PREFIX}/api/stamps`).subscribe(
      (stamps: StampsModel) => {
        this.stamps.next(stamps.stamps);
      },
      (error) => this.stamps.error(error)
    );
  }

  selectById(stampId: number): StampModel | undefined {
    return this.stamps.getValue().find((stamp) => stamp.id == stampId);
  }

  createStamp(requestBody: StampCreateRequest) {
    return this.httpBaseService.postRequest<any>(
      `${environment.API_PREFIX}/api/stamps`,
      requestBody
    );
  }

  deleteStamp(stampId: number) {
    return this.httpBaseService.deleteRequest<any>(
      `${environment.API_PREFIX}/api/stamps/${stampId}`
    );
  }

  downloadAttachment(attachmentId: number) {
    return this.httpBaseService.getRequestAsBlob(
      `${environment.API_PREFIX}/api/stamps/attachments/${attachmentId}`
    );
  }
}
