import { IProperty } from 'app/shared/model//property.model';
import { IBuilding } from 'app/shared/model//building.model';
import { IPropertyUnit } from 'app/shared/model//property-unit.model';

export interface ILandLord {
    id?: number;
    contactId?: number;
    percentageOwnership?: number;
    ownershipType?: number;
    properties?: IProperty[];
    buildings?: IBuilding[];
    propertyUnits?: IPropertyUnit[];
}

export class LandLord implements ILandLord {
    constructor(
        public id?: number,
        public contactId?: number,
        public percentageOwnership?: number,
        public ownershipType?: number,
        public properties?: IProperty[],
        public buildings?: IBuilding[],
        public propertyUnits?: IPropertyUnit[]
    ) {}
}
