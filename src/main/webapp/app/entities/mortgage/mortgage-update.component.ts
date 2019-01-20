import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IMortgage } from 'app/shared/model/mortgage.model';
import { MortgageService } from './mortgage.service';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';

@Component({
    selector: 'jhi-mortgage-update',
    templateUrl: './mortgage-update.component.html'
})
export class MortgageUpdateComponent implements OnInit {
    mortgage: IMortgage;
    isSaving: boolean;

    properties: IProperty[];

    buildings: IBuilding[];

    propertyunits: IPropertyUnit[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mortgageService: MortgageService,
        protected propertyService: PropertyService,
        protected buildingService: BuildingService,
        protected propertyUnitService: PropertyUnitService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mortgage }) => {
            this.mortgage = mortgage;
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mortgage.id !== undefined) {
            this.subscribeToSaveResponse(this.mortgageService.update(this.mortgage));
        } else {
            this.subscribeToSaveResponse(this.mortgageService.create(this.mortgage));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMortgage>>) {
        result.subscribe((res: HttpResponse<IMortgage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
