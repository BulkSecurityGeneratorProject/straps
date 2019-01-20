import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IAppliance } from 'app/shared/model/appliance.model';
import { ApplianceService } from './appliance.service';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';

@Component({
    selector: 'jhi-appliance-update',
    templateUrl: './appliance-update.component.html'
})
export class ApplianceUpdateComponent implements OnInit {
    appliance: IAppliance;
    isSaving: boolean;

    propertyunits: IPropertyUnit[];
    warrantyStartDateDp: any;
    warrantyEndDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected applianceService: ApplianceService,
        protected propertyUnitService: PropertyUnitService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appliance }) => {
            this.appliance = appliance;
        });
        this.propertyUnitService.query().subscribe(
            (res: HttpResponse<IPropertyUnit[]>) => {
                this.propertyunits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.appliance.id !== undefined) {
            this.subscribeToSaveResponse(this.applianceService.update(this.appliance));
        } else {
            this.subscribeToSaveResponse(this.applianceService.create(this.appliance));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppliance>>) {
        result.subscribe((res: HttpResponse<IAppliance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPropertyUnitById(index: number, item: IPropertyUnit) {
        return item.id;
    }
}
