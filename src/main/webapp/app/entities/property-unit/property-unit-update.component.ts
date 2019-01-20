import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from './property-unit.service';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';
import { IMortgage } from 'app/shared/model/mortgage.model';
import { MortgageService } from 'app/entities/mortgage';
import { IRentRoll } from 'app/shared/model/rent-roll.model';
import { RentRollService } from 'app/entities/rent-roll';
import { ILease } from 'app/shared/model/lease.model';
import { LeaseService } from 'app/entities/lease';
import { ILandLord } from 'app/shared/model/land-lord.model';
import { LandLordService } from 'app/entities/land-lord';

@Component({
    selector: 'jhi-property-unit-update',
    templateUrl: './property-unit-update.component.html'
})
export class PropertyUnitUpdateComponent implements OnInit {
    propertyUnit: IPropertyUnit;
    isSaving: boolean;

    buildings: IBuilding[];

    addresses: IAddress[];

    usagetypes: ILookup[];

    statuses: ILookup[];

    mortgages: IMortgage[];

    rentrolls: IRentRoll[];

    leases: ILease[];

    landlords: ILandLord[];
    lastRenovatedDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected propertyUnitService: PropertyUnitService,
        protected buildingService: BuildingService,
        protected addressService: AddressService,
        protected lookupService: LookupService,
        protected mortgageService: MortgageService,
        protected rentRollService: RentRollService,
        protected leaseService: LeaseService,
        protected landLordService: LandLordService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ propertyUnit }) => {
            this.propertyUnit = propertyUnit;
        });
        this.buildingService.query().subscribe(
            (res: HttpResponse<IBuilding[]>) => {
                this.buildings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.addressService.query({ filter: 'propertyunit-is-null' }).subscribe(
            (res: HttpResponse<IAddress[]>) => {
                if (!this.propertyUnit.addressId) {
                    this.addresses = res.body;
                } else {
                    this.addressService.find(this.propertyUnit.addressId).subscribe(
                        (subRes: HttpResponse<IAddress>) => {
                            this.addresses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lookupService.query({ filter: 'propertyunit-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.propertyUnit.usageTypeId) {
                    this.usagetypes = res.body;
                } else {
                    this.lookupService.find(this.propertyUnit.usageTypeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.usagetypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lookupService.query({ filter: 'propertyunit-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.propertyUnit.statusId) {
                    this.statuses = res.body;
                } else {
                    this.lookupService.find(this.propertyUnit.statusId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.statuses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.mortgageService.query({ filter: 'propertyunit-is-null' }).subscribe(
            (res: HttpResponse<IMortgage[]>) => {
                if (!this.propertyUnit.mortgageId) {
                    this.mortgages = res.body;
                } else {
                    this.mortgageService.find(this.propertyUnit.mortgageId).subscribe(
                        (subRes: HttpResponse<IMortgage>) => {
                            this.mortgages = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rentRollService.query({ filter: 'propertyunit-is-null' }).subscribe(
            (res: HttpResponse<IRentRoll[]>) => {
                if (!this.propertyUnit.rentRollId) {
                    this.rentrolls = res.body;
                } else {
                    this.rentRollService.find(this.propertyUnit.rentRollId).subscribe(
                        (subRes: HttpResponse<IRentRoll>) => {
                            this.rentrolls = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.leaseService.query().subscribe(
            (res: HttpResponse<ILease[]>) => {
                this.leases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.landLordService.query().subscribe(
            (res: HttpResponse<ILandLord[]>) => {
                this.landlords = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.propertyUnit.id !== undefined) {
            this.subscribeToSaveResponse(this.propertyUnitService.update(this.propertyUnit));
        } else {
            this.subscribeToSaveResponse(this.propertyUnitService.create(this.propertyUnit));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropertyUnit>>) {
        result.subscribe((res: HttpResponse<IPropertyUnit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBuildingById(index: number, item: IBuilding) {
        return item.id;
    }

    trackAddressById(index: number, item: IAddress) {
        return item.id;
    }

    trackLookupById(index: number, item: ILookup) {
        return item.id;
    }

    trackMortgageById(index: number, item: IMortgage) {
        return item.id;
    }

    trackRentRollById(index: number, item: IRentRoll) {
        return item.id;
    }

    trackLeaseById(index: number, item: ILease) {
        return item.id;
    }

    trackLandLordById(index: number, item: ILandLord) {
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
