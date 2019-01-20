import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IServiceRequest } from 'app/shared/model/service-request.model';
import { ServiceRequestService } from './service-request.service';

@Component({
    selector: 'jhi-service-request-update',
    templateUrl: './service-request-update.component.html'
})
export class ServiceRequestUpdateComponent implements OnInit {
    serviceRequest: IServiceRequest;
    isSaving: boolean;
    requestDateTime: string;

    constructor(protected serviceRequestService: ServiceRequestService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ serviceRequest }) => {
            this.serviceRequest = serviceRequest;
            this.requestDateTime =
                this.serviceRequest.requestDateTime != null ? this.serviceRequest.requestDateTime.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.serviceRequest.requestDateTime = this.requestDateTime != null ? moment(this.requestDateTime, DATE_TIME_FORMAT) : null;
        if (this.serviceRequest.id !== undefined) {
            this.subscribeToSaveResponse(this.serviceRequestService.update(this.serviceRequest));
        } else {
            this.subscribeToSaveResponse(this.serviceRequestService.create(this.serviceRequest));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRequest>>) {
        result.subscribe((res: HttpResponse<IServiceRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
