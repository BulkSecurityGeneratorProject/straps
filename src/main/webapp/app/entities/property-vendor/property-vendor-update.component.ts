import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPropertyVendor } from 'app/shared/model/property-vendor.model';
import { PropertyVendorService } from './property-vendor.service';

@Component({
    selector: 'jhi-property-vendor-update',
    templateUrl: './property-vendor-update.component.html'
})
export class PropertyVendorUpdateComponent implements OnInit {
    propertyVendor: IPropertyVendor;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;

    constructor(protected propertyVendorService: PropertyVendorService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ propertyVendor }) => {
            this.propertyVendor = propertyVendor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.propertyVendor.id !== undefined) {
            this.subscribeToSaveResponse(this.propertyVendorService.update(this.propertyVendor));
        } else {
            this.subscribeToSaveResponse(this.propertyVendorService.create(this.propertyVendor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropertyVendor>>) {
        result.subscribe((res: HttpResponse<IPropertyVendor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
