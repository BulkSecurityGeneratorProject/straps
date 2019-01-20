import { Moment } from 'moment';

export interface IWarranty {
    id?: number;
    description?: string;
    serialNum?: string;
    warrantyStartDate?: Moment;
    warrantyEndDate?: Moment;
}

export class Warranty implements IWarranty {
    constructor(
        public id?: number,
        public description?: string,
        public serialNum?: string,
        public warrantyStartDate?: Moment,
        public warrantyEndDate?: Moment
    ) {}
}
