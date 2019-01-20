import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITenant } from 'app/shared/model/tenant.model';
import { TenantService } from './tenant.service';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from 'app/entities/property-unit';

@Component({
    selector: 'jhi-tenant-update',
    templateUrl: './tenant-update.component.html'
})
export class TenantUpdateComponent implements OnInit {
    tenant: ITenant;
    isSaving: boolean;

    propertyunits: IPropertyUnit[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tenantService: TenantService,
        protected propertyUnitService: PropertyUnitService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tenant }) => {
            this.tenant = tenant;
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
        if (this.tenant.id !== undefined) {
            this.subscribeToSaveResponse(this.tenantService.update(this.tenant));
        } else {
            this.subscribeToSaveResponse(this.tenantService.create(this.tenant));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITenant>>) {
        result.subscribe((res: HttpResponse<ITenant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
