import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropertyVendor } from 'app/shared/model/property-vendor.model';

type EntityResponseType = HttpResponse<IPropertyVendor>;
type EntityArrayResponseType = HttpResponse<IPropertyVendor[]>;

@Injectable({ providedIn: 'root' })
export class PropertyVendorService {
    public resourceUrl = SERVER_API_URL + 'api/property-vendors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/property-vendors';

    constructor(protected http: HttpClient) {}

    create(propertyVendor: IPropertyVendor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(propertyVendor);
        return this.http
            .post<IPropertyVendor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(propertyVendor: IPropertyVendor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(propertyVendor);
        return this.http
            .put<IPropertyVendor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPropertyVendor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPropertyVendor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPropertyVendor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(propertyVendor: IPropertyVendor): IPropertyVendor {
        const copy: IPropertyVendor = Object.assign({}, propertyVendor, {
            startDate:
                propertyVendor.startDate != null && propertyVendor.startDate.isValid()
                    ? propertyVendor.startDate.format(DATE_FORMAT)
                    : null,
            endDate: propertyVendor.endDate != null && propertyVendor.endDate.isValid() ? propertyVendor.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((propertyVendor: IPropertyVendor) => {
                propertyVendor.startDate = propertyVendor.startDate != null ? moment(propertyVendor.startDate) : null;
                propertyVendor.endDate = propertyVendor.endDate != null ? moment(propertyVendor.endDate) : null;
            });
        }
        return res;
    }
}
