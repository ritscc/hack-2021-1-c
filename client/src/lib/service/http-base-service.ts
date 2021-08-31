import axios from "axios";

import { AuthService } from "./auth-service";

export class HttpBaseService {
  private static options = {
    headers: {
      "Content-Type": "application/json",
      Authorization: AuthService.getCredentials(),
    },
  };

  public static getRequest<T>(uri: string) {
    return axios.get<T>(uri, this.options);
  }

  public static postRequest<T>(uri: string, requstBody: any) {
    return axios.post<T>(uri, requstBody, this.options);
  }

  public static putRequest<T>(uri: string, requstBody: any) {
    return axios.put<T>(uri, requstBody, this.options);
  }

  public static deleteRequest<T>(uri: string) {
    return axios.delete<T>(uri, this.options);
  }
}
