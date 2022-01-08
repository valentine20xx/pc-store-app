import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {delay, of, switchMap} from "rxjs";
import {generateId} from "../utils/utils";
import {loadInternalOrders, loadInternalOrdersFailure, loadInternalOrdersSuccess} from "./app.actions";

@Injectable()
export class InternalOrderEffects {
  constructor(private actions$: Actions) {
  }

  counter = 1;

  loadInternalOrders = createEffect(() =>
    this.actions$.pipe(
      ofType(loadInternalOrders.type),
      delay(2000),
      switchMap(() => {
          if (this.counter > 2) {
            this.counter = 1;

            return of(loadInternalOrdersSuccess({
              payload: {
                internalOrders: [{
                  id: generateId(),
                  version: new Date(),
                  client: 'Ololo, Trololo',
                  personalComputer: 'I7, 6700XT',
                  status: 'open',
                  dateOfReceiving: new Date(),
                }],
                loading: false,
                hasError: false
              }
            }));
          } else {
            this.counter += 1;
            return of(loadInternalOrdersFailure());
          }
        }
      )
    )
  );


  // this.httpClient.get('assets/appsettings.json').pipe(
  //   map((response: AppConfig) => ({
  //     type: AppActionTypes.LoadConfigSuccess,
  //     payload: response
  //   })),
  //   catchError(() => of({ type: AppActionTypes.LoadConfigError }))
  // )


  // loadInternalOrders2 = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType('AAA'),
  //     delay(2000),
  //     switchMap(() => {
  //         const internalOrdersTest = [{
  //           id: generateId(),
  //           version: new Date(),
  //           client: 'Ololo, Trololo',
  //           personalComputer: 'I7, 6700XT',
  //           status: 'open',
  //           dateOfReceiving: new Date(),
  //         }];
  //         return of(new AAASuccess({internalOrders: internalOrdersTest}));
  //       }
  //     ))
  // );
  //
  // loadInternalOrders = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType(InternalOrderTypes.LoadInternalOrders),
  //     delay(2000),
  //     switchMap(() => {
  //         const internalOrdersTest = [{
  //           id: generateId(),
  //           version: new Date(),
  //           client: 'Ololo, Trololo',
  //           personalComputer: 'I7, 6700XT',
  //           status: 'open',
  //           dateOfReceiving: new Date(),
  //         }];
  //         if (true)
  //           return of(new LoadInternalOrdersSuccess({internalOrders: internalOrdersTest}));
  //         else
  //           return of(new LoadInternalOrdersFailure());
  //       }
  //     ))
  // );
  //
  // loadInternalOrder = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType(InternalOrderTypes.LoadInternalOrder),
  //     delay(2000),
  //     switchMap((value) => {
  //         const internalOrderTest: InternalOrderDTO = {
  //           id: value.internalOrderId,
  //           version: new Date(),
  //           clientData: {
  //             id: generateId(),
  //             version: new Date(),
  //             name: 'NAME-TEST',
  //             surname: 'asdasdad',
  //             salutationId: 'asdasd'
  //           },
  //           personalComputer: {
  //             id: generateId(),
  //             version: new Date(),
  //             processor: 'proc',
  //             graphicsCard: 'gpu'
  //           }
  //         };
  //
  //         // if (true)
  //         return of(new LoadInternalOrderSuccess({internalOrder: internalOrderTest}));
  //         // else
  //         // return of(new LoadInternalOrderFailure());
  //       }
  //     ))
  // );
}
