import { Moment } from 'moment';
import { IIncomeProjection } from 'app/shared/model//income-projection.model';
import { ITenant } from 'app/shared/model//tenant.model';
import { IAmenity } from 'app/shared/model//amenity.model';
import { IExpense } from 'app/shared/model//expense.model';
import { IMortgage } from 'app/shared/model//mortgage.model';
import { IAppliance } from 'app/shared/model//appliance.model';
import { IRentRoll } from 'app/shared/model//rent-roll.model';
import { ILease } from 'app/shared/model//lease.model';
import { ILandLord } from 'app/shared/model//land-lord.model';

export interface IPropertyUnit {
    id?: number;
    unitNo?: string;
    description?: string;
    floors?: number;
    netArea?: number;
    grossArea?: number;
    residential?: boolean;
    totalRooms?: number;
    noOfBrs?: number;
    noOfFb?: number;
    noOfHb?: number;
    propertyMailboxNum?: string;
    propertyParkingLotNum?: string;
    gasHeating?: boolean;
    whoPaysHeating?: number;
    electric?: boolean;
    whoPaysElectric?: number;
    water?: boolean;
    whoPaysWater?: number;
    lastRenovated?: Moment;
    currentRentPerSqft?: number;
    version?: number;
    buildingId?: number;
    addressId?: number;
    usageTypeId?: number;
    statusId?: number;
    mortgageId?: number;
    rentRollId?: number;
    projectedIncomes?: IIncomeProjection[];
    tenants?: ITenant[];
    amenities?: IAmenity[];
    expenses?: IExpense[];
    mortgages?: IMortgage[];
    appliances?: IAppliance[];
    rentRolls?: IRentRoll[];
    leases?: ILease[];
    owners?: ILandLord[];
}

export class PropertyUnit implements IPropertyUnit {
    constructor(
        public id?: number,
        public unitNo?: string,
        public description?: string,
        public floors?: number,
        public netArea?: number,
        public grossArea?: number,
        public residential?: boolean,
        public totalRooms?: number,
        public noOfBrs?: number,
        public noOfFb?: number,
        public noOfHb?: number,
        public propertyMailboxNum?: string,
        public propertyParkingLotNum?: string,
        public gasHeating?: boolean,
        public whoPaysHeating?: number,
        public electric?: boolean,
        public whoPaysElectric?: number,
        public water?: boolean,
        public whoPaysWater?: number,
        public lastRenovated?: Moment,
        public currentRentPerSqft?: number,
        public version?: number,
        public buildingId?: number,
        public addressId?: number,
        public usageTypeId?: number,
        public statusId?: number,
        public mortgageId?: number,
        public rentRollId?: number,
        public projectedIncomes?: IIncomeProjection[],
        public tenants?: ITenant[],
        public amenities?: IAmenity[],
        public expenses?: IExpense[],
        public mortgages?: IMortgage[],
        public appliances?: IAppliance[],
        public rentRolls?: IRentRoll[],
        public leases?: ILease[],
        public owners?: ILandLord[]
    ) {
        this.residential = this.residential || false;
        this.gasHeating = this.gasHeating || false;
        this.electric = this.electric || false;
        this.water = this.water || false;
    }
}
