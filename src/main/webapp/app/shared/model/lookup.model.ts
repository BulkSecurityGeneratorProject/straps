export interface ILookup {
    id?: number;
    companyId?: number;
    lookupTypesId?: number;
    lookupCode?: string;
    lookupValue?: string;
    flex1?: string;
    flex2?: string;
    flex3?: string;
    flex4?: string;
    version?: string;
    contractId?: number;
}

export class Lookup implements ILookup {
    constructor(
        public id?: number,
        public companyId?: number,
        public lookupTypesId?: number,
        public lookupCode?: string,
        public lookupValue?: string,
        public flex1?: string,
        public flex2?: string,
        public flex3?: string,
        public flex4?: string,
        public version?: string,
        public contractId?: number
    ) {}
}
