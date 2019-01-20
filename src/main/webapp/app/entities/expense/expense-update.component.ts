import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IExpense } from 'app/shared/model/expense.model';
import { ExpenseService } from './expense.service';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';

@Component({
    selector: 'jhi-expense-update',
    templateUrl: './expense-update.component.html'
})
export class ExpenseUpdateComponent implements OnInit {
    expense: IExpense;
    isSaving: boolean;

    properties: IProperty[];

    buildings: IBuilding[];

    propertyunits: IPropertyUnit[];

    types: ILookup[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected expenseService: ExpenseService,
        protected propertyService: PropertyService,
        protected buildingService: BuildingService,
        protected propertyUnitService: PropertyUnitService,
        protected lookupService: LookupService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ expense }) => {
            this.expense = expense;
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
        this.lookupService.query({ filter: 'expense-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.expense.typeId) {
                    this.types = res.body;
                } else {
                    this.lookupService.find(this.expense.typeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.types = [subRes.body].concat(res.body);
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
        if (this.expense.id !== undefined) {
            this.subscribeToSaveResponse(this.expenseService.update(this.expense));
        } else {
            this.subscribeToSaveResponse(this.expenseService.create(this.expense));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpense>>) {
        result.subscribe((res: HttpResponse<IExpense>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
