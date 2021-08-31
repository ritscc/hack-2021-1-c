import axios from "axios";
import { parseCookies, setCookie, destroyCookie } from "nookies";

import { environment } from "../environments/environment";
import { LoginRequest } from "../request/login-request";
import { AccessTokenModel } from "../model/user-model";

export class AuthService {
  public static login(requestBody: LoginRequest) {
    return axios
      .post<AccessTokenModel>(
        `${environment.API_BASE_URI}/api/login`,
        requestBody
      )
      .then((res) => {
        const accesToken = res.data.accessToken;
        const tokenType = res.data.tokenType;

        AuthService.setCredentials(`${tokenType} ${accesToken}`);
      });
  }

  public static setCredentials(credentials: string): void {
    setCookie(null, environment.CREDENTIALS_KEY, credentials, {
      maxAge: 30 * 24 * 60 * 60,
    });
  }

  public static getCredentials(): string | undefined {
    const cookie = parseCookies(null);
    return cookie[environment.CREDENTIALS_KEY];
  }

  public static deleteCredentials(): void {
    destroyCookie(null, environment.CREDENTIALS_KEY);
  }
}
