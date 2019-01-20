export interface IAmenity {
    id?: number;
    description?: string;
    propertyId?: number;
    buildingId?: number;
    propertyUnitId?: number;
}

export class Amenity implements IAmenity {
    constructor(
        public id?: number,
        public description?: string,
        public propertyId?: number,
        public buildingId?: number,
        public propertyUnitId?: number
    ) {}
}
