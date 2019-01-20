import { Moment } from 'moment';

export interface IAppliance {
    id?: number;
    description?: string;
    serialNum?: string;
    warrantyStartDate?: Moment;
    warrantyEndDate?: Moment;
    propertyUnitId?: number;
}

export class Appliance implements IAppliance {
    constructor(
        public id?: number,
        public description?: string,
        public serialNum?: string,
        public warrantyStartDate?: Moment,
        public warrantyEndDate?: Moment,
        public propertyUnitId?: number
    ) {}
}
