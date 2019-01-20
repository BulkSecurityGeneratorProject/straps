export interface IContact {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phonePrimary?: string;
    phoneSecondary?: string;
    contactType?: number;
    version?: string;
    companyId?: number;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phonePrimary?: string,
        public phoneSecondary?: string,
        public contactType?: number,
        public version?: string,
        public companyId?: number
    ) {}
}
