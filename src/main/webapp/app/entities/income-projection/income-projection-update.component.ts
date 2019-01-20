import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIncomeProjection } from 'app/shared/model/income-projection.model';
import { IncomeProjectionService } from './income-projection.service';
import { IPortfolio } from 'app/shared/model/portfolio.model';
import { PortfolioService } from 'app/entities/portfolio';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';

@Component({
    selector: 'jhi-income-projection-update',
    templateUrl: './income-projection-update.component.html'
})
export class IncomeProjectionUpdateComponent implements OnInit {
    incomeProjection: IIncomeProjection;
    isSaving: boolean;

    portfolios: IPortfolio[];

    properties: IProperty[];

    buildings: IBuilding[];

    propertyunits: IPropertyUnit[];

    assetleveltypes: ILookup[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected incomeProjectionService: IncomeProjectionService,
        protected portfolioService: PortfolioService,
        protected propertyService: PropertyService,
        protected buildingService: BuildingService,
        protected propertyUnitService: PropertyUnitService,
        protected lookupService: LookupService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ incomeProjection }) => {
            this.incomeProjection = incomeProjection;
        });
        this.portfolioService.query().subscribe(
            (res: HttpResponse<IPortfolio[]>) => {
                this.portfolios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.lookupService.query({ filter: 'incomeprojection-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.incomeProjection.assetLevelTypeId) {
                    this.assetleveltypes = res.body;
                } else {
                    this.lookupService.find(this.incomeProjection.assetLevelTypeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.assetleveltypes = [subRes.body].concat(res.body);
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
        if (this.incomeProjection.id !== undefined) {
            this.subscribeToSaveResponse(this.incomeProjectionService.update(this.incomeProjection));
        } else {
            this.subscribeToSaveResponse(this.incomeProjectionService.create(this.incomeProjection));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeProjection>>) {
        result.subscribe((res: HttpResponse<IIncomeProjection>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPortfolioById(index: number, item: IPortfolio) {
        return item.id;
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
