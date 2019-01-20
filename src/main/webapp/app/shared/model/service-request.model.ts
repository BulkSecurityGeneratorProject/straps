import { Moment } from 'moment';

export interface IServiceRequest {
    id?: number;
    companyId?: number;
    propertyUnitsId?: number;
    propertiesId?: number;
    propertyVendorsId?: number;
    category?: number;
    subCategory?: number;
    urgency?: number;
    unitId?: number;
    propertyId?: number;
    requestDateTime?: Moment;
    description?: string;
    assignmentStatus?: number;
    version?: string;
}

export class ServiceRequest implements IServiceRequest {
    constructor(
        public id?: number,
        public companyId?: number,
        public propertyUnitsId?: number,
        public propertiesId?: number,
        public propertyVendorsId?: number,
        public category?: number,
        public subCategory?: number,
        public urgency?: number,
        public unitId?: number,
        public propertyId?: number,
        public requestDateTime?: Moment,
        public description?: string,
        public assignmentStatus?: number,
        public version?: string
    ) {}
}
