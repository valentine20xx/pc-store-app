import {Pipe, PipeTransform} from '@angular/core';
import {IdValuePair} from '../../model/model';

@Pipe({
  name: 'referenceConverter'
})
export class ReferenceConverterPipe implements PipeTransform {
  transform(value: string, objects: IdValuePair[] | null): string {
    if (objects == null) {
      return '';
    } else {
      let object = objects.find(object => object.id === value);

      if (object == null) {
        return '';
      } else {
        return object.value
      }
    }
  }
}
