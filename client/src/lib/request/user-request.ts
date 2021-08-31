import { UserRoleEnum } from "../enums/user-role-enum";

// ユーザ作成APIのリクエストボディ
export interface UserCreateRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  roleId: UserRoleEnum;
}

// ユーザ更新APIのリクエストボディ
export interface UserUpdateRequest {
  email: string;
  firstName: string;
  lastName: string;
  roleId: UserRoleEnum;
}

// ログインユーザ更新APIのリクエストボディ
export interface LoginUserUpdateRequest {
  email: string;
  firstName: string;
  lastName: string;
}

// ログインユーザパスワード更新APIのリクエストボディ
export interface LoginUserPasswordRequest {
  currentPassword: string;
  newPassword: string;
}
