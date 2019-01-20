import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IContract } from 'app/shared/model/contract.model';
import { ContractService } from 'app/entities/contract';

@Component({
    selector: 'jhi-company-update',
    templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
    company: ICompany;
    isSaving: boolean;

    types: ILookup[];

    properties: IProperty[];

    buildings: IBuilding[];

    contracts: IContract[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected companyService: CompanyService,
        protected lookupService: LookupService,
        protected propertyService: PropertyService,
        protected buildingService: BuildingService,
        protected contractService: ContractService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ company }) => {
            this.company = company;
        });
        this.lookupService.query({ filter: 'company-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.company.typeId) {
                    this.types = res.body;
                } else {
                    this.lookupService.find(this.company.typeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.types = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
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
        this.contractService.query().subscribe(
            (res: HttpResponse<IContract[]>) => {
                this.contracts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.company.id !== undefined) {
            this.subscribeToSaveResponse(this.companyService.update(this.company));
        } else {
            this.subscribeToSaveResponse(this.companyService.create(this.company));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>) {
        result.subscribe((res: HttpResponse<ICompany>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPropertyById(index: number, item: IProperty) {
        return item.id;
    }

    trackBuildingById(index: number, item: IBuilding) {
        return item.id;
    }

    trackContractById(index: number, item: IContract) {
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
