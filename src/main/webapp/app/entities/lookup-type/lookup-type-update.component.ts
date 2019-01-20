import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ILookupType } from 'app/shared/model/lookup-type.model';
import { LookupTypeService } from './lookup-type.service';

@Component({
    selector: 'jhi-lookup-type-update',
    templateUrl: './lookup-type-update.component.html'
})
export class LookupTypeUpdateComponent implements OnInit {
    lookupType: ILookupType;
    isSaving: boolean;

    constructor(protected lookupTypeService: LookupTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lookupType }) => {
            this.lookupType = lookupType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lookupType.id !== undefined) {
            this.subscribeToSaveResponse(this.lookupTypeService.update(this.lookupType));
        } else {
            this.subscribeToSaveResponse(this.lookupTypeService.create(this.lookupType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookupType>>) {
        result.subscribe((res: HttpResponse<ILookupType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
