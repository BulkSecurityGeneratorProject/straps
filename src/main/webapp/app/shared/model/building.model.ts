import { Moment } from 'moment';
import { IIncomeProjection } from 'app/shared/model//income-projection.model';
import { IPropertyUnit } from 'app/shared/model//property-unit.model';
import { IAmenity } from 'app/shared/model//amenity.model';
import { IExpense } from 'app/shared/model//expense.model';
import { IMortgage } from 'app/shared/model//mortgage.model';
import { IRentRoll } from 'app/shared/model//rent-roll.model';
import { ILandLord } from 'app/shared/model//land-lord.model';
import { ICompany } from 'app/shared/model//company.model';
import { ILease } from 'app/shared/model//lease.model';

export interface IBuilding {
    id?: number;
    name?: string;
    yearBuilt?: Moment;
    noOfloors?: number;
    buildingSize?: number;
    coveredArea?: number;
    landArea?: number;
    noOfRentalUnit?: number;
    parkingSpaces?: number;
    totalSpaceAvaliable?: number;
    totalUnitLevel?: number;
    currentRentPerSqft?: number;
    description?: string;
    version?: string;
    propertyId?: number;
    assetTypeId?: number;
    projectedIncomes?: IIncomeProjection[];
    propertyUnits?: IPropertyUnit[];
    amenities?: IAmenity[];
    expenses?: IExpense[];
    mortgages?: IMortgage[];
    rentRolls?: IRentRoll[];
    owners?: ILandLord[];
    companies?: ICompany[];
    leases?: ILease[];
}

export class Building implements IBuilding {
    constructor(
        public id?: number,
        public name?: string,
        public yearBuilt?: Moment,
        public noOfloors?: number,
        public buildingSize?: number,
        public coveredArea?: number,
        public landArea?: number,
        public noOfRentalUnit?: number,
        public parkingSpaces?: number,
        public totalSpaceAvaliable?: number,
        public totalUnitLevel?: number,
        public currentRentPerSqft?: number,
        public description?: string,
        public version?: string,
        public propertyId?: number,
        public assetTypeId?: number,
        public projectedIncomes?: IIncomeProjection[],
        public propertyUnits?: IPropertyUnit[],
        public amenities?: IAmenity[],
        public expenses?: IExpense[],
        public mortgages?: IMortgage[],
        public rentRolls?: IRentRoll[],
        public owners?: ILandLord[],
        public companies?: ICompany[],
        public leases?: ILease[]
    ) {}
}
