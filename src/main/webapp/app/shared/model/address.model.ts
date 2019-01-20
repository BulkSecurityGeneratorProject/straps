export interface IAddress {
    id?: number;
    locationId?: number;
    locationTypeId?: number;
    addressTypeId?: number;
    description?: string;
    addressline1?: string;
    addressline2?: string;
    addressline3?: string;
    city?: string;
    state?: string;
    postalCode?: string;
    countryCode?: string;
    country?: string;
    phoneNumber?: string;
    tollFreeNumber?: string;
    afterHoursNumber?: string;
    faxNumber?: string;
    emailAddress?: string;
    phonePrefix?: string;
    geocode?: string;
    version?: string;
    companyId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public locationId?: number,
        public locationTypeId?: number,
        public addressTypeId?: number,
        public description?: string,
        public addressline1?: string,
        public addressline2?: string,
        public addressline3?: string,
        public city?: string,
        public state?: string,
        public postalCode?: string,
        public countryCode?: string,
        public country?: string,
        public phoneNumber?: string,
        public tollFreeNumber?: string,
        public afterHoursNumber?: string,
        public faxNumber?: string,
        public emailAddress?: string,
        public phonePrefix?: string,
        public geocode?: string,
        public version?: string,
        public companyId?: number
    ) {}
}
