import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILandLord } from 'app/shared/model/land-lord.model';
import { LandLordService } from './land-lord.service';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';

@Component({
    selector: 'jhi-land-lord-update',
    templateUrl: './land-lord-update.component.html'
})
export class LandLordUpdateComponent implements OnInit {
    landLord: ILandLord;
    isSaving: boolean;

    properties: IProperty[];

    buildings: IBuilding[];

    propertyunits: IPropertyUnit[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected landLordService: LandLordService,
        protected propertyService: PropertyService,
        protected buildingService: BuildingService,
        protected propertyUnitService: PropertyUnitService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ landLord }) => {
            this.landLord = landLord;
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
        if (this.landLord.id !== undefined) {
            this.subscribeToSaveResponse(this.landLordService.update(this.landLord));
        } else {
            this.subscribeToSaveResponse(this.landLordService.create(this.landLord));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILandLord>>) {
        result.subscribe((res: HttpResponse<ILandLord>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
