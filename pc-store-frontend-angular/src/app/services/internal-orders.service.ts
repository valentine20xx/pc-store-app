import {Injectable} from '@angular/core';
import {delay, Observable, of, throwError} from 'rxjs';
import {InternalOrderShortDTO} from '../model/model';
import {generateId} from '../utils/utils';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InternalOrdersService implements InternalOrders {
  constructor(private httpClient: HttpClient) {
  }

  getInternalOrders(): Observable<InternalOrderShortDTO[]> {
    console.warn("Prod");
    // return of([]);

    return this.httpClient.get<InternalOrderShortDTO[]>('http://localhost:8080/internal-order-list')
  }

  addInternalOrderMP(newInternalOrderMP: any, formData: FormData): Observable<any> {
    console.debug(newInternalOrderMP);

    return this.httpClient.post('http://localhost:8080/internal-order-multipart', formData);
  }
}

export interface InternalOrders {
  getInternalOrders(): Observable<InternalOrderShortDTO[]>;

  addInternalOrderMP(newInternalOrderMP: any, formData: FormData): Observable<any>;
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

  addInternalOrderMP(newInternalOrderMP: any, formData: FormData): Observable<any> {
    console.debug(newInternalOrderMP);

    return of();
  }
}
