import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';

type EntityResponseType = HttpResponse<IPropertyUnit>;
type EntityArrayResponseType = HttpResponse<IPropertyUnit[]>;

@Injectable({ providedIn: 'root' })
export class PropertyUnitService {
    public resourceUrl = SERVER_API_URL + 'api/property-units';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/property-units';

    constructor(protected http: HttpClient) {}

    create(propertyUnit: IPropertyUnit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(propertyUnit);
        return this.http
            .post<IPropertyUnit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(propertyUnit: IPropertyUnit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(propertyUnit);
        return this.http
            .put<IPropertyUnit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPropertyUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPropertyUnit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPropertyUnit[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(propertyUnit: IPropertyUnit): IPropertyUnit {
        const copy: IPropertyUnit = Object.assign({}, propertyUnit, {
            lastRenovated:
                propertyUnit.lastRenovated != null && propertyUnit.lastRenovated.isValid()
                    ? propertyUnit.lastRenovated.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.lastRenovated = res.body.lastRenovated != null ? moment(res.body.lastRenovated) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((propertyUnit: IPropertyUnit) => {
                propertyUnit.lastRenovated = propertyUnit.lastRenovated != null ? moment(propertyUnit.lastRenovated) : null;
            });
        }
        return res;
    }
}
