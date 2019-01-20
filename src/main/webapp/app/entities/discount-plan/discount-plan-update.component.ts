import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDiscountPlan } from 'app/shared/model/discount-plan.model';
import { DiscountPlanService } from './discount-plan.service';

@Component({
    selector: 'jhi-discount-plan-update',
    templateUrl: './discount-plan-update.component.html'
})
export class DiscountPlanUpdateComponent implements OnInit {
    discountPlan: IDiscountPlan;
    isSaving: boolean;

    constructor(protected discountPlanService: DiscountPlanService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ discountPlan }) => {
            this.discountPlan = discountPlan;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.discountPlan.id !== undefined) {
            this.subscribeToSaveResponse(this.discountPlanService.update(this.discountPlan));
        } else {
            this.subscribeToSaveResponse(this.discountPlanService.create(this.discountPlan));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscountPlan>>) {
        result.subscribe((res: HttpResponse<IDiscountPlan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
