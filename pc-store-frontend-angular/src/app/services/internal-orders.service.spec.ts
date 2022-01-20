import { TestBed } from '@angular/core/testing';

import { InternalOrdersService } from './internal-orders.service';

describe('InternalOrdersService', () => {
  let service: InternalOrdersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternalOrdersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
