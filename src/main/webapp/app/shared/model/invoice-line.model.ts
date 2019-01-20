export interface IInvoiceLine {
    id?: number;
    lineNum?: number;
    invoiceId?: number;
    planId?: number;
    categoryId?: number;
    description?: string;
    amount?: number;
    currency?: number;
    rate?: number;
    quantity?: number;
}

export class InvoiceLine implements IInvoiceLine {
    constructor(
        public id?: number,
        public lineNum?: number,
        public invoiceId?: number,
        public planId?: number,
        public categoryId?: number,
        public description?: string,
        public amount?: number,
        public currency?: number,
        public rate?: number,
        public quantity?: number
    ) {}
}
