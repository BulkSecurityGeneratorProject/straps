export interface IIncomeProjection {
    id?: number;
    period?: number;
    projectedValue?: number;
    portfolioId?: number;
    propertyId?: number;
    buildingId?: number;
    propertyUnitId?: number;
    assetLevelTypeId?: number;
}

export class IncomeProjection implements IIncomeProjection {
    constructor(
        public id?: number,
        public period?: number,
        public projectedValue?: number,
        public portfolioId?: number,
        public propertyId?: number,
        public buildingId?: number,
        public propertyUnitId?: number,
        public assetLevelTypeId?: number
    ) {}
}
