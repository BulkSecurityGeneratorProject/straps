import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvoiceHeader } from 'app/shared/model/invoice-header.model';

type EntityResponseType = HttpResponse<IInvoiceHeader>;
type EntityArrayResponseType = HttpResponse<IInvoiceHeader[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceHeaderService {
    public resourceUrl = SERVER_API_URL + 'api/invoice-headers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/invoice-headers';

    constructor(protected http: HttpClient) {}

    create(invoiceHeader: IInvoiceHeader): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invoiceHeader);
        return this.http
            .post<IInvoiceHeader>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(invoiceHeader: IInvoiceHeader): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invoiceHeader);
        return this.http
            .put<IInvoiceHeader>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInvoiceHeader>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvoiceHeader[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvoiceHeader[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(invoiceHeader: IInvoiceHeader): IInvoiceHeader {
        const copy: IInvoiceHeader = Object.assign({}, invoiceHeader, {
            invoiceDate:
                invoiceHeader.invoiceDate != null && invoiceHeader.invoiceDate.isValid()
                    ? invoiceHeader.invoiceDate.format(DATE_FORMAT)
                    : null,
            fromDate:
                invoiceHeader.fromDate != null && invoiceHeader.fromDate.isValid() ? invoiceHeader.fromDate.format(DATE_FORMAT) : null,
            toDate: invoiceHeader.toDate != null && invoiceHeader.toDate.isValid() ? invoiceHeader.toDate.format(DATE_FORMAT) : null,
            paymentDate:
                invoiceHeader.paymentDate != null && invoiceHeader.paymentDate.isValid()
                    ? invoiceHeader.paymentDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.invoiceDate = res.body.invoiceDate != null ? moment(res.body.invoiceDate) : null;
            res.body.fromDate = res.body.fromDate != null ? moment(res.body.fromDate) : null;
            res.body.toDate = res.body.toDate != null ? moment(res.body.toDate) : null;
            res.body.paymentDate = res.body.paymentDate != null ? moment(res.body.paymentDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((invoiceHeader: IInvoiceHeader) => {
                invoiceHeader.invoiceDate = invoiceHeader.invoiceDate != null ? moment(invoiceHeader.invoiceDate) : null;
                invoiceHeader.fromDate = invoiceHeader.fromDate != null ? moment(invoiceHeader.fromDate) : null;
                invoiceHeader.toDate = invoiceHeader.toDate != null ? moment(invoiceHeader.toDate) : null;
                invoiceHeader.paymentDate = invoiceHeader.paymentDate != null ? moment(invoiceHeader.paymentDate) : null;
            });
        }
        return res;
    }
}
