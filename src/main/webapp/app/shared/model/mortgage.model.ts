import { Moment } from 'moment';

export interface IMortgage {
    id?: number;
    companyId?: number;
    propertyUnitsId?: number;
    borrower?: number;
    lender?: number;
    startDate?: Moment;
    endDate?: Moment;
    amount?: number;
    interestRate?: number;
    balloonPayment?: number;
    refinanceOption?: boolean;
    amortizationLengthYrs?: number;
    version?: string;
    propertyId?: number;
    buildingId?: number;
    propertyUnitId?: number;
}

export class Mortgage implements IMortgage {
    constructor(
        public id?: number,
        public companyId?: number,
        public propertyUnitsId?: number,
        public borrower?: number,
        public lender?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public amount?: number,
        public interestRate?: number,
        public balloonPayment?: number,
        public refinanceOption?: boolean,
        public amortizationLengthYrs?: number,
        public version?: string,
        public propertyId?: number,
        public buildingId?: number,
        public propertyUnitId?: number
    ) {
        this.refinanceOption = this.refinanceOption || false;
    }
}
