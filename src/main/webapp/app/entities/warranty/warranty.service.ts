import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWarranty } from 'app/shared/model/warranty.model';

type EntityResponseType = HttpResponse<IWarranty>;
type EntityArrayResponseType = HttpResponse<IWarranty[]>;

@Injectable({ providedIn: 'root' })
export class WarrantyService {
    public resourceUrl = SERVER_API_URL + 'api/warranties';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/warranties';

    constructor(protected http: HttpClient) {}

    create(warranty: IWarranty): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(warranty);
        return this.http
            .post<IWarranty>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(warranty: IWarranty): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(warranty);
        return this.http
            .put<IWarranty>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWarranty>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWarranty[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWarranty[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(warranty: IWarranty): IWarranty {
        const copy: IWarranty = Object.assign({}, warranty, {
            warrantyStartDate:
                warranty.warrantyStartDate != null && warranty.warrantyStartDate.isValid()
                    ? warranty.warrantyStartDate.format(DATE_FORMAT)
                    : null,
            warrantyEndDate:
                warranty.warrantyEndDate != null && warranty.warrantyEndDate.isValid() ? warranty.warrantyEndDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.warrantyStartDate = res.body.warrantyStartDate != null ? moment(res.body.warrantyStartDate) : null;
            res.body.warrantyEndDate = res.body.warrantyEndDate != null ? moment(res.body.warrantyEndDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((warranty: IWarranty) => {
                warranty.warrantyStartDate = warranty.warrantyStartDate != null ? moment(warranty.warrantyStartDate) : null;
                warranty.warrantyEndDate = warranty.warrantyEndDate != null ? moment(warranty.warrantyEndDate) : null;
            });
        }
        return res;
    }
}
