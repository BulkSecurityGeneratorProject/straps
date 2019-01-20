export interface IDocumentStore {
    id?: number;
    entityId?: number;
    entityName?: string;
    path?: string;
    category?: number;
    subCategory?: number;
    version?: string;
}

export class DocumentStore implements IDocumentStore {
    constructor(
        public id?: number,
        public entityId?: number,
        public entityName?: string,
        public path?: string,
        public category?: number,
        public subCategory?: number,
        public version?: string
    ) {}
}
