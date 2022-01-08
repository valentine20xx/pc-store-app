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
