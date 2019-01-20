export interface ILookupType {
    id?: number;
    companyId?: number;
    lookupTypeCode?: string;
    lookupTypeValue?: string;
    flex1Descr?: string;
    flex2Descr?: string;
    flex3Descr?: string;
    flex4Descr?: string;
    version?: string;
}

export class LookupType implements ILookupType {
    constructor(
        public id?: number,
        public companyId?: number,
        public lookupTypeCode?: string,
        public lookupTypeValue?: string,
        public flex1Descr?: string,
        public flex2Descr?: string,
        public flex3Descr?: string,
        public flex4Descr?: string,
        public version?: string
    ) {}
}
