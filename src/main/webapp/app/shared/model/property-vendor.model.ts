import { Moment } from 'moment';

export interface IPropertyVendor {
    id?: number;
    companyId?: number;
    ranking?: number;
    startDate?: Moment;
    endDate?: Moment;
    version?: string;
}

export class PropertyVendor implements IPropertyVendor {
    constructor(
        public id?: number,
        public companyId?: number,
        public ranking?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public version?: string
    ) {}
}
