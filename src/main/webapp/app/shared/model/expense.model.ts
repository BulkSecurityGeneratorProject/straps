export interface IExpense {
    id?: number;
    amount?: number;
    currency?: number;
    propertyId?: number;
    buildingId?: number;
    propertyUnitId?: number;
    typeId?: number;
}

export class Expense implements IExpense {
    constructor(
        public id?: number,
        public amount?: number,
        public currency?: number,
        public propertyId?: number,
        public buildingId?: number,
        public propertyUnitId?: number,
        public typeId?: number
    ) {}
}
