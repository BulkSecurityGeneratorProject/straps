export interface IDiscountPlan {
    id?: number;
    planId?: number;
    planName?: string;
    appliedToPlan?: number;
    appliedWithPlan?: number;
    discountRate?: number;
    discountUnit?: number;
    maxDiscountAmt?: number;
    description?: string;
    conditional?: boolean;
}

export class DiscountPlan implements IDiscountPlan {
    constructor(
        public id?: number,
        public planId?: number,
        public planName?: string,
        public appliedToPlan?: number,
        public appliedWithPlan?: number,
        public discountRate?: number,
        public discountUnit?: number,
        public maxDiscountAmt?: number,
        public description?: string,
        public conditional?: boolean
    ) {
        this.conditional = this.conditional || false;
    }
}
