import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IBillingPlan } from 'app/shared/model/billing-plan.model';
import { BillingPlanService } from './billing-plan.service';

@Component({
    selector: 'jhi-billing-plan-update',
    templateUrl: './billing-plan-update.component.html'
})
export class BillingPlanUpdateComponent implements OnInit {
    billingPlan: IBillingPlan;
    isSaving: boolean;
    effectiveDateDp: any;

    constructor(protected billingPlanService: BillingPlanService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ billingPlan }) => {
            this.billingPlan = billingPlan;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.billingPlan.id !== undefined) {
            this.subscribeToSaveResponse(this.billingPlanService.update(this.billingPlan));
        } else {
            this.subscribeToSaveResponse(this.billingPlanService.create(this.billingPlan));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingPlan>>) {
        result.subscribe((res: HttpResponse<IBillingPlan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
