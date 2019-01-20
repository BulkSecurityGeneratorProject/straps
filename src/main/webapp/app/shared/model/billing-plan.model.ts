import { Moment } from 'moment';

export interface IBillingPlan {
    id?: number;
    planName?: string;
    category?: number;
    memberType?: number;
    description?: string;
    prorationDesc?: string;
    effectiveDate?: Moment;
    effectiveStatus?: number;
    association?: string;
    orderType?: number;
    accountingBook?: number;
    rates?: number;
    currency?: number;
    basis?: number;
    initiationFees?: number;
    coupons?: string;
    prorated?: boolean;
    glcode?: string;
    active?: boolean;
}

export class BillingPlan implements IBillingPlan {
    constructor(
        public id?: number,
        public planName?: string,
        public category?: number,
        public memberType?: number,
        public description?: string,
        public prorationDesc?: string,
        public effectiveDate?: Moment,
        public effectiveStatus?: number,
        public association?: string,
        public orderType?: number,
        public accountingBook?: number,
        public rates?: number,
        public currency?: number,
        public basis?: number,
        public initiationFees?: number,
        public coupons?: string,
        public prorated?: boolean,
        public glcode?: string,
        public active?: boolean
    ) {
        this.prorated = this.prorated || false;
        this.active = this.active || false;
    }
}
