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
