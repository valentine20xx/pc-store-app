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

export interface NewInternalOrderMPDTO {
  clientData: {
    salutation: 'male' | 'female';
    name: string;
    surname: string;
  };
  personalComputer: {
    processor: string;
    graphicsCard: string;
  };
  privacyPolicy: boolean;
  files: Array<{
    id: string;
    name: string;
    note: string;
  }>;
}

export function getInternalOrderStatuses(): IdValuePair[] {
  return [
    {id: 'open', value: 'Open'},
    {id: 'checked', value: 'Checked'},
    {id: 'producing', value: 'Producing'},
    {id: 'produced', value: 'Produced'},
    {id: 'sent', value: 'Sent'},
    {id: 'closed', value: 'Closed'}
  ];
}

export function getSalutations(): IdValuePair[] {
  return [
    {id: 'male', value: 'Male'},
    {id: 'female', value: 'Female'}
  ];
}
