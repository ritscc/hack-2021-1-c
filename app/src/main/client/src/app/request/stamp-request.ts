// スタンプ作成APIのリクエストボディ
export interface StampCreateRequest {
  title: string;
  description: string;
  attachments: AttachmentRequestModel[] | never[];
}

interface AttachmentRequestModel {
  name: string;
  content: string;
}
