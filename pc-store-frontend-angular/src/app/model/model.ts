import {Observable, of} from "rxjs";

export interface InternalOrderShortDTO extends DefaultDTOObject {
  client: string;
  personalComputer: string;
  status: string;
  dateOfReceiving: Date;
}

export interface InternalOrderDTO extends DefaultDTOObject {
  clientData: ClientDataDTO;
  personalComputer: PersonalComputerDTO;
}

export interface ClientDataDTO extends DefaultDTOObject {
  salutationId: string;
  name: string;
  surname: string;
}

export interface PersonalComputerDTO extends DefaultDTOObject {
  processor: string;
  graphicsCard: string;
}


export interface DefaultDTOObject {
  id: string;
  version: Date;
}

export interface IdValuePair {
  id: string;
  value: string;
}

export function getInternalOrderStatuses(): IdValuePair[] {
  return [{id: 'open', value: 'Open'}];
}

export function getSalutations(): IdValuePair[] {
  return [{id: 'male', value: 'Male'}, {id: 'female', value: 'Female'}];
}
