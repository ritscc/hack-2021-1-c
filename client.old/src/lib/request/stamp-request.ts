// スタンプ作成APIのリクエストボディ
export interface StampCreateRequest {
  title: string;
  description: string;
  attachments: {
    name: string;
    content: string;
  };
}
