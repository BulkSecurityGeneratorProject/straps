import { IIncomeProjection } from 'app/shared/model//income-projection.model';
import { IProperty } from 'app/shared/model//property.model';

export interface IPortfolio {
    id?: number;
    description?: string;
    projectedIncomes?: IIncomeProjection[];
    properties?: IProperty[];
}

export class Portfolio implements IPortfolio {
    constructor(
        public id?: number,
        public description?: string,
        public projectedIncomes?: IIncomeProjection[],
        public properties?: IProperty[]
    ) {}
}
