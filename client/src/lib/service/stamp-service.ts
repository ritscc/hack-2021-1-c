import { HttpBaseService } from "./http-base-service";

import { environment } from "../environments/environment";
import { StampsModel } from "../model/stamp-model";
import { StampCreateRequest } from "../request/stamp-request";

export class StampService {
  public static getStamps() {
    return HttpBaseService.getRequest<StampsModel>(
      `${environment.API_BASE_URI}/api/stamps`
    );
  }

  public static createStamp(requestBody: StampCreateRequest) {
    return HttpBaseService.postRequest(
      `${environment.API_BASE_URI}/api/stamps`,
      requestBody
    );
  }

  public static deleteStamp(stampId: number) {
    return HttpBaseService.deleteRequest(
      `${environment.API_BASE_URI}/api/stamps/${stampId}`
    );
  }

  public static downloadStamp(attachmentId: number) {
    return HttpBaseService.getRequest(
      `${environment.API_BASE_URI}/api/stamps/attachments/${attachmentId}`
    );
  }
}
