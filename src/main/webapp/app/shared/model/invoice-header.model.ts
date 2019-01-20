import { Moment } from 'moment';

export interface IInvoiceHeader {
    id?: number;
    planId?: number;
    serviceproviderId?: number;
    invoiceNum?: number;
    invoiceDate?: Moment;
    invoiceStatus?: number;
    fromDate?: Moment;
    toDate?: Moment;
    amount?: number;
    fees?: number;
    currency?: number;
    paymentTerms?: number;
    paymentStatus?: number;
    paymentMethod?: number;
    paymentDate?: Moment;
    paymentAmount?: number;
    paymentRef?: string;
    comments?: string;
    adhoc?: boolean;
    billToCompany?: number;
    legacy?: boolean;
    payor?: string;
    paymentComments?: string;
    emailDate?: string;
    emailAddress?: string;
    keyHash?: string;
}

export class InvoiceHeader implements IInvoiceHeader {
    constructor(
        public id?: number,
        public planId?: number,
        public serviceproviderId?: number,
        public invoiceNum?: number,
        public invoiceDate?: Moment,
        public invoiceStatus?: number,
        public fromDate?: Moment,
        public toDate?: Moment,
        public amount?: number,
        public fees?: number,
        public currency?: number,
        public paymentTerms?: number,
        public paymentStatus?: number,
        public paymentMethod?: number,
        public paymentDate?: Moment,
        public paymentAmount?: number,
        public paymentRef?: string,
        public comments?: string,
        public adhoc?: boolean,
        public billToCompany?: number,
        public legacy?: boolean,
        public payor?: string,
        public paymentComments?: string,
        public emailDate?: string,
        public emailAddress?: string,
        public keyHash?: string
    ) {
        this.adhoc = this.adhoc || false;
        this.legacy = this.legacy || false;
    }
}
