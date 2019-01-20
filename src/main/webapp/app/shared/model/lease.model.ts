import { Moment } from 'moment';
import { IPropertyUnit } from 'app/shared/model//property-unit.model';
import { IBuilding } from 'app/shared/model//building.model';

export interface ILease {
    id?: number;
    leaseSignedDate?: Moment;
    landlordsId?: number;
    landlordAgent?: number;
    tenantsId?: number;
    addressId?: number;
    numOccupants?: number;
    leaseTerm?: number;
    leaseStartDate?: Moment;
    leaseEndDate?: Moment;
    rentAmount?: number;
    rentPeriod?: number;
    totalTermRent?: number;
    rentEscalationPerc?: number;
    proRataStartDate?: number;
    proRataRentAmount?: number;
    proRataEndDate?: Moment;
    additionalCharges?: number;
    securityDeposit?: number;
    petsAllowed?: boolean;
    petType?: number;
    petDescription?: string;
    water?: boolean;
    gas?: boolean;
    electric?: boolean;
    otherUtilities?: string;
    terminationNoticePeriod?: number;
    agencyCompany?: number;
    managementCompany?: number;
    propertyId?: number;
    additionalNotes?: string;
    typeId?: number;
    propertyUnits?: IPropertyUnit[];
    buildings?: IBuilding[];
}

export class Lease implements ILease {
    constructor(
        public id?: number,
        public leaseSignedDate?: Moment,
        public landlordsId?: number,
        public landlordAgent?: number,
        public tenantsId?: number,
        public addressId?: number,
        public numOccupants?: number,
        public leaseTerm?: number,
        public leaseStartDate?: Moment,
        public leaseEndDate?: Moment,
        public rentAmount?: number,
        public rentPeriod?: number,
        public totalTermRent?: number,
        public rentEscalationPerc?: number,
        public proRataStartDate?: number,
        public proRataRentAmount?: number,
        public proRataEndDate?: Moment,
        public additionalCharges?: number,
        public securityDeposit?: number,
        public petsAllowed?: boolean,
        public petType?: number,
        public petDescription?: string,
        public water?: boolean,
        public gas?: boolean,
        public electric?: boolean,
        public otherUtilities?: string,
        public terminationNoticePeriod?: number,
        public agencyCompany?: number,
        public managementCompany?: number,
        public propertyId?: number,
        public additionalNotes?: string,
        public typeId?: number,
        public propertyUnits?: IPropertyUnit[],
        public buildings?: IBuilding[]
    ) {
        this.petsAllowed = this.petsAllowed || false;
        this.water = this.water || false;
        this.gas = this.gas || false;
        this.electric = this.electric || false;
    }
}
