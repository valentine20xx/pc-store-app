import {Injectable} from '@angular/core';
import {delay, Observable, of, throwError} from 'rxjs';
import {InternalOrderDTO, InternalOrderShortDTO, NewInternalOrderMPDTO} from '../model/model';
import {generateId} from '../utils/utils';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InternalOrdersService implements InternalOrders {
  constructor(private httpClient: HttpClient) {
  }

  getInternalOrders(): Observable<InternalOrderShortDTO[]> {
    return this.httpClient.get<InternalOrderShortDTO[]>('http://localhost:8080/internal-order-list')
  }

  addInternalOrderMP(newInternalOrderMP: NewInternalOrderMPDTO, formData: FormData): Observable<InternalOrderDTO> {
    return this.httpClient.post<InternalOrderDTO>('http://localhost:8080/internal-order-multipart', formData);
  }

  getInternalOrder(id: string): Observable<InternalOrderDTO> {
    return this.httpClient.get<InternalOrderDTO>(`http://localhost:8080/internal-order/${id}`);
  }
}

export interface InternalOrders {
  getInternalOrders(): Observable<InternalOrderShortDTO[]>;

  getInternalOrder(id: string): Observable<InternalOrderDTO>;

  addInternalOrderMP(newInternalOrderMP: any, formData: FormData): Observable<any>;
}

export class InternalOrdersServiceLocal implements InternalOrders {
  counter = 1;

  constructor() {
  }

  getInternalOrders(): Observable<InternalOrderShortDTO[]> {
    console.warn('Local');

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

  getInternalOrder(id: string): Observable<InternalOrderDTO> {
    return of<InternalOrderDTO>({
      id: generateId(),
      version: new Date(),
      personalComputer: {
        id: generateId(),
        version: new Date(),
        graphicsCard: 'graphicsCard',
        processor: 'processor',
        computerCase: 'computerCase',
        motherboard: 'motherboard',
        powerSupplyUnit: 'powerSupplyUnit',
        randomAccessMemory: 'randomAccessMemory',
        storageDevice: 'storageDevice'
      },
      clientData: {
        id: generateId(),
        version: new Date(),
        salutation: 'male',
        name: 'name',
        surname: 'surname',
        zip: '',
        city: '',
        email: '',
        houseNumber: '',
        street: '',
        cellphone: '',
        telephone: ''
      },
      internalOrderFiles: []
    });
  }
}
