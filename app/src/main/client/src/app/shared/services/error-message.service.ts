import { Injectable } from '@angular/core';

interface MessageDict {
  [key: number]: string;
}

@Injectable({
  providedIn: 'root',
})
export class ErrorMessageService {
  messages: MessageDict = {
    401: 'ユーザが認証されていません',
    403: 'その動作は許可されていません',
    404: '存在しないURLへはアクセスできません',
    500: '予期せぬエラーが発生しました',
    1000: '予期せぬエラーが発生しました',
    1100: 'APIが見つかりません',
    1101: '存在しないユーザです',
    1102: '存在しないロールです',
    1103: 'スタンプが見つかりません',
    1104: '添付ファイルが見つかりません',
    1200: 'メールアドレスは既に登録済みです',
    1300: 'その動作は許可されていません',
    1400: '無効なリクエストです',
    1401: 'パスワードの長さは8~32文字にしてください',
    1402: 'パスワードが単純すぎます',
    1500: 'ユーザはログインしていません',
    1501: 'パスワードが間違っています',
    1502: '無効なアクセストークンです',
    1503: '期限切れのアクセストークンです',
  };

  constructor() {}

  getErrorMessage(statusCode: number): string {
    if (statusCode in this.messages) {
      return this.messages[statusCode];
    } else {
      return this.messages[500];
    }
  }
}
