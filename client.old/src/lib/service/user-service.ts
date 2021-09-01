import { HttpBaseService } from "./http-base-service";

import { environment } from "../environments/environment";
import { UserModel, UsersModel } from "../model/user-model";
import {
  LoginUserUpdateRequest,
  LoginUserPasswordUpdateRequest,
  UserCreateRequest,
  UserUpdateRequest,
} from "../request/user-request";

export class UserService {
  public static getUsers() {
    return HttpBaseService.getRequest<UsersModel>(
      `${environment.API_BASE_URI}/api/users`
    );
  }

  public static createUser(requestBody: UserCreateRequest) {
    return HttpBaseService.postRequest(
      `${environment.API_BASE_URI}/api/users`,
      requestBody
    );
  }

  public static updateUser(userId: number, requestBody: UserUpdateRequest) {
    return HttpBaseService.putRequest(
      `${environment.API_BASE_URI}/api/users/${userId}`,
      requestBody
    );
  }

  public static deleteUser(userId: number) {
    return HttpBaseService.deleteRequest(
      `${environment.API_BASE_URI}/api/users/${userId}`
    );
  }

  public static getLoginUser() {
    return HttpBaseService.getRequest<UserModel>(
      `${environment.API_BASE_URI}/api/users/me`
    );
  }

  public static updateLoginUser(requestBody: LoginUserUpdateRequest) {
    return HttpBaseService.putRequest(
      `${environment.API_BASE_URI}/api/users/me`,
      requestBody
    );
  }

  public static updateLoginUserPassword(
    requestBody: LoginUserPasswordUpdateRequest
  ) {
    return HttpBaseService.putRequest(
      `${environment.API_BASE_URI}/api/users/me/password`,
      requestBody
    );
  }
}
