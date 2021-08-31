// スタンプモデル
export interface StampModel {
  id: number;
  title: string;
  description: string;
  userId: number;
  attachments: StampAttachmentModel[];
}

// 添付ファイルモデル
export interface StampAttachmentModel {
  id: number;
  name: string;
  uuid: string;
}
