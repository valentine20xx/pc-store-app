import {Pipe, PipeTransform} from '@angular/core';
import {IdNamePair} from '../../internal-orders-overview/new-internal-order/new-internal-order.component';

@Pipe({
  name: 'referenceConverter'
})
export class ReferenceConverterPipe implements PipeTransform {
  transform(value: string, objects: IdNamePair[] | null): string {
    if (objects == null) {
      return '';
    } else {
      let object = objects.find(object => object.id === value);

      if (object == null) {
        return '';
      } else {
        return object.name
      }
    }
  }
}
