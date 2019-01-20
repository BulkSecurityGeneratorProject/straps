import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IWarranty } from 'app/shared/model/warranty.model';
import { WarrantyService } from './warranty.service';

@Component({
    selector: 'jhi-warranty-update',
    templateUrl: './warranty-update.component.html'
})
export class WarrantyUpdateComponent implements OnInit {
    warranty: IWarranty;
    isSaving: boolean;
    warrantyStartDateDp: any;
    warrantyEndDateDp: any;

    constructor(protected warrantyService: WarrantyService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ warranty }) => {
            this.warranty = warranty;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.warranty.id !== undefined) {
            this.subscribeToSaveResponse(this.warrantyService.update(this.warranty));
        } else {
            this.subscribeToSaveResponse(this.warrantyService.create(this.warranty));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarranty>>) {
        result.subscribe((res: HttpResponse<IWarranty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
