// スタンプモデル
export interface StampModel {
  id: number;
  title: string;
  description: string;
  userId: number;
  attachments: StampAttachmentModel[];
}

// スタンプ一覧モデル
export interface StampsModel {
  stamps: StampModel[];
}

// 添付ファイルモデル
export interface StampAttachmentModel {
  id: number;
  name: string;
  uuid: string;
}
