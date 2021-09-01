import { UserRoleEnum } from "../enums/user-role-enum";

// ユーザモデル
export interface UserModel {
  id: number;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  roleId: UserRoleEnum;
}

// ユーザ一覧モデル
export interface UsersModel {
  users: UserModel[];
}

// アクセストークンモデル
export interface AccessTokenModel {
  accessToken: string;
  tokenType: string;
}
