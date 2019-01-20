import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiscountPlan } from 'app/shared/model/discount-plan.model';

@Component({
    selector: 'jhi-discount-plan-detail',
    templateUrl: './discount-plan-detail.component.html'
})
export class DiscountPlanDetailComponent implements OnInit {
    discountPlan: IDiscountPlan;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ discountPlan }) => {
            this.discountPlan = discountPlan;
        });
    }

    previousState() {
        window.history.back();
    }
}
