export interface ICompanyCapability {
    id?: number;
    capabilityId?: number;
    license?: number;
    certfication?: number;
    companyId?: number;
}

export class CompanyCapability implements ICompanyCapability {
    constructor(
        public id?: number,
        public capabilityId?: number,
        public license?: number,
        public certfication?: number,
        public companyId?: number
    ) {}
}
