export interface IFeature {
    id?: number;
    description?: string;
    typeId?: number;
}

export class Feature implements IFeature {
    constructor(public id?: number, public description?: string, public typeId?: number) {}
}
