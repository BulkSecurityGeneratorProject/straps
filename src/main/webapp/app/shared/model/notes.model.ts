export interface INotes {
    id?: number;
    note?: string;
}

export class Notes implements INotes {
    constructor(public id?: number, public note?: string) {}
}
