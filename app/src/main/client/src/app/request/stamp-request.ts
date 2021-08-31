// スタンプ作成APIのリクエストボディ
export interface StampCreateRequest {
  title: string;
  description: string;
  attachments: AttachmentRequestModel[];
}

interface AttachmentRequestModel {
  name: string;
  content: string;
}
