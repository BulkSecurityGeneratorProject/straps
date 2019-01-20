import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IInvoiceHeader } from 'app/shared/model/invoice-header.model';
import { InvoiceHeaderService } from './invoice-header.service';

@Component({
    selector: 'jhi-invoice-header-update',
    templateUrl: './invoice-header-update.component.html'
})
export class InvoiceHeaderUpdateComponent implements OnInit {
    invoiceHeader: IInvoiceHeader;
    isSaving: boolean;
    invoiceDateDp: any;
    fromDateDp: any;
    toDateDp: any;
    paymentDateDp: any;

    constructor(protected invoiceHeaderService: InvoiceHeaderService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoiceHeader }) => {
            this.invoiceHeader = invoiceHeader;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.invoiceHeader.id !== undefined) {
            this.subscribeToSaveResponse(this.invoiceHeaderService.update(this.invoiceHeader));
        } else {
            this.subscribeToSaveResponse(this.invoiceHeaderService.create(this.invoiceHeader));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceHeader>>) {
        result.subscribe((res: HttpResponse<IInvoiceHeader>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
