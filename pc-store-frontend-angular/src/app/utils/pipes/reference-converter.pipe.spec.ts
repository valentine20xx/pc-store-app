import {ReferenceConverterPipe} from './reference-converter.pipe';
import {IdNamePair} from "../../internal-orders-overview/new-internal-order/new-internal-order.component";

describe('ReferenceConverterPipe', () => {
  let pipe: ReferenceConverterPipe;

  beforeEach(() => {
    pipe = new ReferenceConverterPipe();
  });

  it('should be instanced', () => {
    expect(pipe).not.toBeNull();
  });

  it('should be converted', (done) => {
    const testArray: IdNamePair[] = [
      {id: 'testId1', name: 'testName1'},
      {id: 'testId2', name: 'testName2'}
    ];
    expect(pipe.transform('testId2', testArray)).toEqual('testName2');
    expect(pipe.transform('testId2', null)).toEqual('');
    done();
  });
});
