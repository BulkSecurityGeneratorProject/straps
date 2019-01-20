export interface IRentRoll {
    id?: number;
    securityDeposit?: number;
    bankGuarantee?: number;
    leaseRecoveryTiming?: number;
    propertyId?: number;
    buildingId?: number;
    propertyUnitId?: number;
    inflationTypeId?: number;
}

export class RentRoll implements IRentRoll {
    constructor(
        public id?: number,
        public securityDeposit?: number,
        public bankGuarantee?: number,
        public leaseRecoveryTiming?: number,
        public propertyId?: number,
        public buildingId?: number,
        public propertyUnitId?: number,
        public inflationTypeId?: number
    ) {}
}
