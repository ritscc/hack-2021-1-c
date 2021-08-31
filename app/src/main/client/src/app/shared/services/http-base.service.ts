import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

import { AuthService } from './auth.service';
import { ErrorMessageService } from './error-message.service';

@Injectable({
  providedIn: 'root',
})
export class HttpBaseService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private errorMessageService: ErrorMessageService
  ) {}

  getRequest<T>(url: string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.authService.getCredentials(),
      }),
    };

    return this.http.get<T>(url, options).pipe(
      catchError((error) => {
        // 無効なJWT
        if (error.status == 401) {
          this.authService.logout();
        }

        throw this.errorMessageService.getErrorMessage(error.error.code);
      })
    );
  }

  postRequest<T>(url: string, requestBody: any) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.authService.getCredentials(),
      }),
    };

    return this.http.post<T>(url, requestBody, options).pipe(
      catchError((error) => {
        // 無効なJWT
        if (error.status == 401) {
          this.authService.logout();
        }

        throw this.errorMessageService.getErrorMessage(error.error.code);
      })
    );
  }

  putRequest<T>(url: string, requestBody: any) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.authService.getCredentials(),
      }),
    };

    return this.http.put<T>(url, requestBody, options).pipe(
      catchError((error) => {
        // 無効なJWT
        if (error.status == 401) {
          this.authService.logout();
        }

        throw this.errorMessageService.getErrorMessage(error.error.code);
      })
    );
  }

  deleteRequest<T>(url: string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.authService.getCredentials(),
      }),
    };

    return this.http.delete<T>(url, options).pipe(
      catchError((error) => {
        // 無効なJWT
        if (error.status == 401) {
          this.authService.logout();
        }

        throw this.errorMessageService.getErrorMessage(error.error.code);
      })
    );
  }

  getRequestAsBlob(url: string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.authService.getCredentials(),
      }),
      responseType: 'blob' as 'json',
    };

    return this.http.get<Blob>(url, options).pipe(
      catchError((error) => {
        console.log(error);
        // 無効なJWT
        if (error.status == 401) {
          this.authService.logout();
        }

        throw this.errorMessageService.getErrorMessage(error.error.code);
      })
    );
  }
}
