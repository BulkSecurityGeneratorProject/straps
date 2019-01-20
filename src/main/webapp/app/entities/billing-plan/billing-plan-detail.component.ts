import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingPlan } from 'app/shared/model/billing-plan.model';

@Component({
    selector: 'jhi-billing-plan-detail',
    templateUrl: './billing-plan-detail.component.html'
})
export class BillingPlanDetailComponent implements OnInit {
    billingPlan: IBillingPlan;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billingPlan }) => {
            this.billingPlan = billingPlan;
        });
    }

    previousState() {
        window.history.back();
    }
}
