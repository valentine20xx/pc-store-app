export interface DefaultDTOObject {
    id: string;
    version: Date;
}

export interface InternalOrderShortDTO extends DefaultDTOObject {
    client: string;
    personalComputer: string;
    status: string;
    dateOfReceiving: Date;
}

export interface NewClientDataDTO {
    salutation: Saltuations | null;
    name: string | null;
    surname: string | null;
    street: string | null;
    houseNumber: string | null;
    zip: string | null;
    city: string | null;
    telephone?: string | null;
    cellphone?: string | null;
    email?: string | null;
}

export interface NewPersonalComputerDTO {
    computerCase: string | null;
    motherboard: string | null;
    processor: string | null;
    graphicsCard: string | null;
    randomAccessMemory: string | null;
    storageDevice: string | null;
    powerSupplyUnit: string | null;
}

export interface NewInternalOrderDTO {
    personalComputer: NewPersonalComputerDTO;
    clientData: NewClientDataDTO;
    privacyPolicy: boolean;
    files: Array<{
        id: string;
        name: string;
        note: string;
    }>;
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

export interface ClientDataDTO extends DefaultDTOObject {
    salutation: Saltuations;
    name: string;
    surname: string;
    street: string;
    houseNumber: string;
    zip: string;
    city: string;
    telephone?: string;
    cellphone?: string;
    email?: string;
}

export enum Saltuations {
    male = "male", female = "female"
}

export interface InternalOrderFileDTO extends DefaultDTOObject {
    name: string;
    note?: string;
}

export interface InternalOrderDTO extends DefaultDTOObject {
    personalComputer: PersonalComputerDTO;
    clientData: ClientDataDTO;
    privacyPolicy: boolean;
    files: Array<InternalOrderFileDTO>;
}

// const rows: InternalOrderShortDTO[] = [
//     {
//         id: generateId(),
//         version: new Date(),
//         client: "Ololo, Trololo",
//         personalComputer: "i7, 6700XT",
//         status: "open",
//         dateOfReceiving: new Date(),
//     }
// ];

export const defaultNewInternalOrderDTO = (): NewInternalOrderDTO => {
    return {
        personalComputer: {
            computerCase: null,
            motherboard: null,
            processor: null,
            graphicsCard: null,
            randomAccessMemory: null,
            storageDevice: null,
            powerSupplyUnit: null
        },
        clientData: {
            salutation: null,
            name: null,
            surname: null,
            street: null,
            houseNumber: null,
            zip: null,
            city: null
        },
        files: [], privacyPolicy: false
    }
}
