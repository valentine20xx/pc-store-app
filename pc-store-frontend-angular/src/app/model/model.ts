export interface InternalOrderShortDTO extends DefaultDTOObject {
  client: string;
  personalComputer: string;
  status: string;
  dateOfReceiving: Date;
}

export interface InternalOrderDTO extends DefaultDTOObject {
  clientData: ClientDataDTO;
  personalComputer: PersonalComputerDTO;
  internalOrderFiles: Array<InternalOrderFileDTO>
}

export interface InternalOrderFileDTO extends DefaultDTOObject {
  name: string;
  note: string;
}

export interface ClientDataDTO extends DefaultDTOObject {
  salutation: 'male' | 'female';
  name: string;
  surname: string;
  street: string;
  houseNumber: string;
  zip: string;
  city: string;
  telephone: string;
  cellphone: string;
  email: string;
}

export interface PersonalComputerDTO extends DefaultDTOObject {
  computerCase: string;
  motherboard: string;
  processor: string;
  graphicsCard: string;
  randomAccessMemory: string;
  storageDevice: string;
  powerSupplyUnit: string;
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
    street: string;
    houseNumber: string;
    zip: string;
    city: string;
    telephone: string;
    cellphone: string;
    email: string;
  };
  personalComputer: {
    computerCase: string;
    motherboard: string;
    processor: string;
    graphicsCard: string;
    randomAccessMemory: string;
    storageDevice: string;
    powerSupplyUnit: string;
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
