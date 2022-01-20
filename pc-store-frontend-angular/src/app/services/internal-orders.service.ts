import {Injectable} from '@angular/core';
import {delay, Observable, of, throwError} from "rxjs";
import {IdValuePair, InternalOrderShortDTO} from "../model/model";
import {generateId} from "../utils/utils";

@Injectable({
  providedIn: 'root'
})
export class InternalOrdersService implements InternalOrders {
  constructor() {
  }

  getInternalOrders(): Observable<InternalOrderShortDTO[]> {
    console.warn("Prod");
    return of([]);
  }


}

export interface InternalOrders {
  getInternalOrders(): Observable<InternalOrderShortDTO[]>;
}

export class InternalOrdersServiceLocal implements InternalOrders {
  counter = 1;

  constructor() {
  }

  getInternalOrders(): Observable<InternalOrderShortDTO[]> {
    console.warn("Local");

    if (this.counter > 1) {
      this.counter = 1;

      return of([{
        id: generateId(),
        version: new Date(),
        client: 'Ololo, Trololo',
        personalComputer: 'i7, 6700XT',
        status: 'open',
        dateOfReceiving: new Date(),
      }]).pipe(delay(2000));
    } else {
      this.counter++;

      return throwError(() => new Error('Errror'));
    }
  }
}
