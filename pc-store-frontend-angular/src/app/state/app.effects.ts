import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, delay, of, switchMap} from 'rxjs';
import {loadInternalOrders, loadInternalOrdersFailure, loadInternalOrdersSuccess, login, loginSuccess, logout, logoutSuccess} from './app.actions';
import {InternalOrdersService} from '../services/internal-orders.service';

@Injectable()
export class InternalOrderEffects {
  constructor(private actions$: Actions,
              private internalOrdersService: InternalOrdersService) {

  }

  loadInternalOrders = createEffect(() =>
    this.actions$.pipe(
      ofType(loadInternalOrders.type),
      switchMap(() => {
          return this.internalOrdersService.getInternalOrders().pipe(
            switchMap(
              value => {
                return of(loadInternalOrdersSuccess({
                  payload: {
                    internalOrders: value,
                    loading: false,
                    hasError: false
                  }
                }));
              }
            ),
            catchError(() =>
              of(loadInternalOrdersFailure())
            )
          );
        }
      )
    )
  );

  loginEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(login.type),
      switchMap(() => {
          return of(loginSuccess({payload: {logged: true, hasError: false, name: 'Test', loading: false}})).pipe(delay(2000));
          // return of(loginFailure()).pipe(delay(2000));
        }
      )
    )
  );

  logoutEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(logout.type),
      switchMap(() => {
          return of(logoutSuccess({payload: {logged: false, hasError: false, name: undefined, loading: false}})).pipe(delay(2000));
        }
      )
    )
  );
}
