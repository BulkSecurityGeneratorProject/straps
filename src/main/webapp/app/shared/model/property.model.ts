import { IIncomeProjection } from 'app/shared/model//income-projection.model';
import { IAmenity } from 'app/shared/model//amenity.model';
import { IExpense } from 'app/shared/model//expense.model';
import { IBuilding } from 'app/shared/model//building.model';
import { IMortgage } from 'app/shared/model//mortgage.model';
import { IRentRoll } from 'app/shared/model//rent-roll.model';
import { ILandLord } from 'app/shared/model//land-lord.model';
import { ICompany } from 'app/shared/model//company.model';

export interface IProperty {
    id?: number;
    description?: string;
    grossArea?: number;
    netArea?: number;
    portfolioId?: number;
    addressId?: number;
    usageTypeId?: number;
    statusId?: number;
    leaseId?: number;
    projectedIncomes?: IIncomeProjection[];
    amenities?: IAmenity[];
    expenses?: IExpense[];
    buildings?: IBuilding[];
    mortgages?: IMortgage[];
    rentRolls?: IRentRoll[];
    owners?: ILandLord[];
    companies?: ICompany[];
}

export class Property implements IProperty {
    constructor(
        public id?: number,
        public description?: string,
        public grossArea?: number,
        public netArea?: number,
        public portfolioId?: number,
        public addressId?: number,
        public usageTypeId?: number,
        public statusId?: number,
        public leaseId?: number,
        public projectedIncomes?: IIncomeProjection[],
        public amenities?: IAmenity[],
        public expenses?: IExpense[],
        public buildings?: IBuilding[],
        public mortgages?: IMortgage[],
        public rentRolls?: IRentRoll[],
        public owners?: ILandLord[],
        public companies?: ICompany[]
    ) {}
}
