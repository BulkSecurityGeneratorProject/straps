import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from './property.service';
import { IPortfolio } from 'app/shared/model/portfolio.model';
import { PortfolioService } from 'app/entities/portfolio';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';
import { ILease } from 'app/shared/model/lease.model';
import { LeaseService } from 'app/entities/lease';
import { ILandLord } from 'app/shared/model/land-lord.model';
import { LandLordService } from 'app/entities/land-lord';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-property-update',
    templateUrl: './property-update.component.html'
})
export class PropertyUpdateComponent implements OnInit {
    property: IProperty;
    isSaving: boolean;

    portfolios: IPortfolio[];

    addresses: IAddress[];

    usagetypes: ILookup[];

    statuses: ILookup[];

    leases: ILease[];

    landlords: ILandLord[];

    companies: ICompany[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected propertyService: PropertyService,
        protected portfolioService: PortfolioService,
        protected addressService: AddressService,
        protected lookupService: LookupService,
        protected leaseService: LeaseService,
        protected landLordService: LandLordService,
        protected companyService: CompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ property }) => {
            this.property = property;
        });
        this.portfolioService.query().subscribe(
            (res: HttpResponse<IPortfolio[]>) => {
                this.portfolios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.addressService.query({ filter: 'property-is-null' }).subscribe(
            (res: HttpResponse<IAddress[]>) => {
                if (!this.property.addressId) {
                    this.addresses = res.body;
                } else {
                    this.addressService.find(this.property.addressId).subscribe(
                        (subRes: HttpResponse<IAddress>) => {
                            this.addresses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lookupService.query({ filter: 'property-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.property.usageTypeId) {
                    this.usagetypes = res.body;
                } else {
                    this.lookupService.find(this.property.usageTypeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.usagetypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lookupService.query({ filter: 'property-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.property.statusId) {
                    this.statuses = res.body;
                } else {
                    this.lookupService.find(this.property.statusId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.statuses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.leaseService.query({ filter: 'property-is-null' }).subscribe(
            (res: HttpResponse<ILease[]>) => {
                if (!this.property.leaseId) {
                    this.leases = res.body;
                } else {
                    this.leaseService.find(this.property.leaseId).subscribe(
                        (subRes: HttpResponse<ILease>) => {
                            this.leases = [subRes.body].concat(res.body);
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.property.id !== undefined) {
            this.subscribeToSaveResponse(this.propertyService.update(this.property));
        } else {
            this.subscribeToSaveResponse(this.propertyService.create(this.property));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProperty>>) {
        result.subscribe((res: HttpResponse<IProperty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAddressById(index: number, item: IAddress) {
        return item.id;
    }

    trackLookupById(index: number, item: ILookup) {
        return item.id;
    }

    trackLeaseById(index: number, item: ILease) {
        return item.id;
    }

    trackLandLordById(index: number, item: ILandLord) {
        return item.id;
    }

    trackCompanyById(index: number, item: ICompany) {
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
