import { IAddress } from 'app/shared/model//address.model';
import { IContact } from 'app/shared/model//contact.model';
import { ICompanyCapability } from 'app/shared/model//company-capability.model';
import { IProperty } from 'app/shared/model//property.model';
import { IBuilding } from 'app/shared/model//building.model';
import { IContract } from 'app/shared/model//contract.model';

export interface ICompany {
    id?: number;
    name?: string;
    description?: string;
    version?: string;
    typeId?: number;
    addresses?: IAddress[];
    contacts?: IContact[];
    capabilities?: ICompanyCapability[];
    properties?: IProperty[];
    buildings?: IBuilding[];
    contracts?: IContract[];
    managementCompanies?: IContract[];
}

export class Company implements ICompany {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public version?: string,
        public typeId?: number,
        public addresses?: IAddress[],
        public contacts?: IContact[],
        public capabilities?: ICompanyCapability[],
        public properties?: IProperty[],
        public buildings?: IBuilding[],
        public contracts?: IContract[],
        public managementCompanies?: IContract[]
    ) {}
}
