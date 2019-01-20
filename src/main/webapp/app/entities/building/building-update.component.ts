import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from './building.service';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';
import { ILandLord } from 'app/shared/model/land-lord.model';
import { LandLordService } from 'app/entities/land-lord';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';
import { ILease } from 'app/shared/model/lease.model';
import { LeaseService } from 'app/entities/lease';

@Component({
    selector: 'jhi-building-update',
    templateUrl: './building-update.component.html'
})
export class BuildingUpdateComponent implements OnInit {
    building: IBuilding;
    isSaving: boolean;

    properties: IProperty[];

    assettypes: ILookup[];

    landlords: ILandLord[];

    companies: ICompany[];

    leases: ILease[];
    yearBuiltDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected buildingService: BuildingService,
        protected propertyService: PropertyService,
        protected lookupService: LookupService,
        protected landLordService: LandLordService,
        protected companyService: CompanyService,
        protected leaseService: LeaseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ building }) => {
            this.building = building;
        });
        this.propertyService.query().subscribe(
            (res: HttpResponse<IProperty[]>) => {
                this.properties = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lookupService.query({ filter: 'building-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.building.assetTypeId) {
                    this.assettypes = res.body;
                } else {
                    this.lookupService.find(this.building.assetTypeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.assettypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.landLordService.query().subscribe(
            (res: HttpResponse<ILandLord[]>) => {
                this.landlords = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.companyService.query().subscribe(
            (res: HttpResponse<ICompany[]>) => {
                this.companies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.leaseService.query().subscribe(
            (res: HttpResponse<ILease[]>) => {
                this.leases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.building.id !== undefined) {
            this.subscribeToSaveResponse(this.buildingService.update(this.building));
        } else {
            this.subscribeToSaveResponse(this.buildingService.create(this.building));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuilding>>) {
        result.subscribe((res: HttpResponse<IBuilding>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLookupById(index: number, item: ILookup) {
        return item.id;
    }

    trackLandLordById(index: number, item: ILandLord) {
        return item.id;
    }

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }

    trackLeaseById(index: number, item: ILease) {
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
