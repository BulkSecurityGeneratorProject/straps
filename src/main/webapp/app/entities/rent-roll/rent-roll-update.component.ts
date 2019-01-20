import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRentRoll } from 'app/shared/model/rent-roll.model';
import { RentRollService } from './rent-roll.service';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';

@Component({
    selector: 'jhi-rent-roll-update',
    templateUrl: './rent-roll-update.component.html'
})
export class RentRollUpdateComponent implements OnInit {
    rentRoll: IRentRoll;
    isSaving: boolean;

    properties: IProperty[];

    buildings: IBuilding[];

    propertyunits: IPropertyUnit[];

    inflationtypes: ILookup[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rentRollService: RentRollService,
        protected propertyService: PropertyService,
        protected buildingService: BuildingService,
        protected propertyUnitService: PropertyUnitService,
        protected lookupService: LookupService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rentRoll }) => {
            this.rentRoll = rentRoll;
        });
        this.propertyService.query().subscribe(
            (res: HttpResponse<IProperty[]>) => {
                this.properties = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.buildingService.query().subscribe(
            (res: HttpResponse<IBuilding[]>) => {
                this.buildings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.propertyUnitService.query().subscribe(
            (res: HttpResponse<IPropertyUnit[]>) => {
                this.propertyunits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lookupService.query({ filter: 'rentroll-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.rentRoll.inflationTypeId) {
                    this.inflationtypes = res.body;
                } else {
                    this.lookupService.find(this.rentRoll.inflationTypeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.inflationtypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rentRoll.id !== undefined) {
            this.subscribeToSaveResponse(this.rentRollService.update(this.rentRoll));
        } else {
            this.subscribeToSaveResponse(this.rentRollService.create(this.rentRoll));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRentRoll>>) {
        result.subscribe((res: HttpResponse<IRentRoll>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPropertyById(index: number, item: IProperty) {
        return item.id;
    }

    trackBuildingById(index: number, item: IBuilding) {
        return item.id;
    }

    trackPropertyUnitById(index: number, item: IPropertyUnit) {
        return item.id;
    }

    trackLookupById(index: number, item: ILookup) {
        return item.id;
    }
}
