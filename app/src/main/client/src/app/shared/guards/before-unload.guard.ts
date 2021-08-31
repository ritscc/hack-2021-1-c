import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import {
  ActivatedRouteSnapshot,
  CanDeactivate,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';

export interface OnBeforeUnload {
  shouldConfirmOnBeforeunload: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class BeforeUnloadGuard implements CanDeactivate<OnBeforeUnload> {
  constructor(
    private location: Location,
    private router: Router,
    public matDialog: MatDialog
  ) {}

  canDeactivate(component: OnBeforeUnload) {
    if (component.shouldConfirmOnBeforeunload) {
      const msg =
        'このページを離れてもよろしいですか？' +
        '\n行った変更が保存されない可能性があります。';
      return confirm(msg);
    }
    return true;
  }
}
