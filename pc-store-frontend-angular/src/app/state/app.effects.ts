import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, delay, Observable, of, switchMap, tap} from 'rxjs';
import {InternalOrdersService} from '../services/internal-orders.service';
import {ILoadInternalOrder, loadInternalOrder, loadInternalOrderClosed, loadInternalOrders, loadInternalOrdersClosed, loadInternalOrdersFailure, loadInternalOrdersSuccess, loadInternalOrderSuccess, login, loginSuccess, logout, logoutSuccess} from './app.actions';

@Injectable()
export class InternalOrderEffects {
  constructor(private actions$: Actions,
              private internalOrdersService: InternalOrdersService) {
  }

  loadInternalOrdersEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(loadInternalOrders.type),
      switchMap(() => {
          return this.internalOrdersService.getInternalOrders().pipe(
            switchMap(
              value => {
                return of(loadInternalOrdersSuccess({
                  internalOrders: value,
                  internalOrders$: of(value)
                }));
              }
            ),
            catchError((err, caught) => {
                return of(loadInternalOrdersFailure({message: err?.statusText}));
              }
            )
          );
        }
      )
    )
  );

  loadInternalOrdersEffectSuccess2Close = createEffect(() =>
    this.actions$.pipe(
      ofType(loadInternalOrdersSuccess.type),
      switchMap(() => {
        return of(loadInternalOrdersClosed())
      })
    )
  );

  loadInternalOrdersEffectFailure2Close = createEffect(() =>
    this.actions$.pipe(
      ofType(loadInternalOrdersFailure.type),
      switchMap(() => {
        return of(loadInternalOrdersClosed())
      })
    )
  );

  loadInternalOrderEffect = createEffect(() => this.actions$.pipe(
    ofType(loadInternalOrder.type),
    switchMap((value: ILoadInternalOrder) => {
      return this.internalOrdersService.getInternalOrder(value.id).pipe(
        switchMap(internalOrderDTO => {
          return of(loadInternalOrderSuccess({internalOrderDTO: internalOrderDTO}))
        })
      )
    })
  ));

  loadInternalOrderEffectSuccess2Close = createEffect(() =>
    this.actions$.pipe(
      ofType(loadInternalOrderSuccess.type),
      switchMap(() => {
        return of(loadInternalOrderClosed())
      })
    )
  );

  loginEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(login.type),
      switchMap(() => {
          // return of(loginSuccess({payload: {status: 'RECEIVED', logged: true, name: 'Test', loading: false}})).pipe(delay(2000));
          return of(loginSuccess({logged: true, name: 'Test'})).pipe(delay(2000));
          // return of(loginFailure()).pipe(delay(2000));
        }
      )
    )
  );

  logoutEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(logout.type),
      switchMap(() => {
          return of(logoutSuccess({logged: false, name: ''})).pipe(delay(2000));
        }
      )
    )
  );
}
