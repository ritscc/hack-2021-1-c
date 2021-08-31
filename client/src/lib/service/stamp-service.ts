import { HttpBaseService } from "./http-base-service";

import { environment } from "../environments/environment";
import { StampModel } from "../model/stamp-model";
import { StampCreateRequest } from "../request/stamp-request";

export class StampService {
  public static getStamps() {
    return HttpBaseService.getRequest<StampModel[]>(
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
    // FIXME: ヘッダーからファイル情報を取り出して
    return HttpBaseService.getRequest(
      `${environment.API_BASE_URI}/api/stamps/attachments/${attachmentId}`
    );
  }
}
