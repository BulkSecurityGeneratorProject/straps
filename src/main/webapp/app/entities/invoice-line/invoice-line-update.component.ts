import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { InvoiceLineService } from './invoice-line.service';

@Component({
    selector: 'jhi-invoice-line-update',
    templateUrl: './invoice-line-update.component.html'
})
export class InvoiceLineUpdateComponent implements OnInit {
    invoiceLine: IInvoiceLine;
    isSaving: boolean;

    constructor(protected invoiceLineService: InvoiceLineService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoiceLine }) => {
            this.invoiceLine = invoiceLine;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.invoiceLine.id !== undefined) {
            this.subscribeToSaveResponse(this.invoiceLineService.update(this.invoiceLine));
        } else {
            this.subscribeToSaveResponse(this.invoiceLineService.create(this.invoiceLine));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceLine>>) {
        result.subscribe((res: HttpResponse<IInvoiceLine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
