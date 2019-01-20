export interface ITenant {
    id?: number;
    leaseId?: number;
    contact?: number;
    propertyUnitId?: number;
}

export class Tenant implements ITenant {
    constructor(public id?: number, public leaseId?: number, public contact?: number, public propertyUnitId?: number) {}
}
