import { Moment } from 'moment';
import { ILookup } from 'app/shared/model//lookup.model';
import { ICompany } from 'app/shared/model//company.model';

export interface IContract {
    id?: number;
    description?: string;
    startDate?: Moment;
    endDate?: Moment;
    types?: ILookup[];
    companies?: ICompany[];
    managementCompanies?: ICompany[];
}

export class Contract implements IContract {
    constructor(
        public id?: number,
        public description?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public types?: ILookup[],
        public companies?: ICompany[],
        public managementCompanies?: ICompany[]
    ) {}
}
