import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ILease } from 'app/shared/model/lease.model';
import { LeaseService } from './lease.service';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';

@Component({
    selector: 'jhi-lease-update',
    templateUrl: './lease-update.component.html'
})
export class LeaseUpdateComponent implements OnInit {
    lease: ILease;
    isSaving: boolean;

    types: ILookup[];

    propertyunits: IPropertyUnit[];

    buildings: IBuilding[];
    leaseSignedDateDp: any;
    leaseStartDateDp: any;
    leaseEndDateDp: any;
    proRataEndDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaseService: LeaseService,
        protected lookupService: LookupService,
        protected propertyUnitService: PropertyUnitService,
        protected buildingService: BuildingService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lease }) => {
            this.lease = lease;
        });
        this.lookupService.query({ filter: 'lease-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.lease.typeId) {
                    this.types = res.body;
                } else {
                    this.lookupService.find(this.lease.typeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.types = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.propertyUnitService.query().subscribe(
            (res: HttpResponse<IPropertyUnit[]>) => {
                this.propertyunits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.buildingService.query().subscribe(
            (res: HttpResponse<IBuilding[]>) => {
                this.buildings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lease.id !== undefined) {
            this.subscribeToSaveResponse(this.leaseService.update(this.lease));
        } else {
            this.subscribeToSaveResponse(this.leaseService.create(this.lease));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILease>>) {
        result.subscribe((res: HttpResponse<ILease>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLookupById(index: number, item: ILookup) {
        return item.id;
    }

    trackPropertyUnitById(index: number, item: IPropertyUnit) {
        return item.id;
    }

    trackBuildingById(index: number, item: IBuilding) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
