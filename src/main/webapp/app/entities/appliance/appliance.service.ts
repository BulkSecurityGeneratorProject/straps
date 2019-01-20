import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAppliance } from 'app/shared/model/appliance.model';

type EntityResponseType = HttpResponse<IAppliance>;
type EntityArrayResponseType = HttpResponse<IAppliance[]>;

@Injectable({ providedIn: 'root' })
export class ApplianceService {
    public resourceUrl = SERVER_API_URL + 'api/appliances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/appliances';

    constructor(protected http: HttpClient) {}

    create(appliance: IAppliance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(appliance);
        return this.http
            .post<IAppliance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(appliance: IAppliance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(appliance);
        return this.http
            .put<IAppliance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAppliance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAppliance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAppliance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(appliance: IAppliance): IAppliance {
        const copy: IAppliance = Object.assign({}, appliance, {
            warrantyStartDate:
                appliance.warrantyStartDate != null && appliance.warrantyStartDate.isValid()
                    ? appliance.warrantyStartDate.format(DATE_FORMAT)
                    : null,
            warrantyEndDate:
                appliance.warrantyEndDate != null && appliance.warrantyEndDate.isValid()
                    ? appliance.warrantyEndDate.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((appliance: IAppliance) => {
                appliance.warrantyStartDate = appliance.warrantyStartDate != null ? moment(appliance.warrantyStartDate) : null;
                appliance.warrantyEndDate = appliance.warrantyEndDate != null ? moment(appliance.warrantyEndDate) : null;
            });
        }
        return res;
    }
}
