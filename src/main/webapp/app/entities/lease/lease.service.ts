import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILease } from 'app/shared/model/lease.model';

type EntityResponseType = HttpResponse<ILease>;
type EntityArrayResponseType = HttpResponse<ILease[]>;

@Injectable({ providedIn: 'root' })
export class LeaseService {
    public resourceUrl = SERVER_API_URL + 'api/leases';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leases';

    constructor(protected http: HttpClient) {}

    create(lease: ILease): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lease);
        return this.http
            .post<ILease>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lease: ILease): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lease);
        return this.http
            .put<ILease>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILease>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILease[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILease[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(lease: ILease): ILease {
        const copy: ILease = Object.assign({}, lease, {
            leaseSignedDate:
                lease.leaseSignedDate != null && lease.leaseSignedDate.isValid() ? lease.leaseSignedDate.format(DATE_FORMAT) : null,
            leaseStartDate:
                lease.leaseStartDate != null && lease.leaseStartDate.isValid() ? lease.leaseStartDate.format(DATE_FORMAT) : null,
            leaseEndDate: lease.leaseEndDate != null && lease.leaseEndDate.isValid() ? lease.leaseEndDate.format(DATE_FORMAT) : null,
            proRataEndDate: lease.proRataEndDate != null && lease.proRataEndDate.isValid() ? lease.proRataEndDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.leaseSignedDate = res.body.leaseSignedDate != null ? moment(res.body.leaseSignedDate) : null;
            res.body.leaseStartDate = res.body.leaseStartDate != null ? moment(res.body.leaseStartDate) : null;
            res.body.leaseEndDate = res.body.leaseEndDate != null ? moment(res.body.leaseEndDate) : null;
            res.body.proRataEndDate = res.body.proRataEndDate != null ? moment(res.body.proRataEndDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lease: ILease) => {
                lease.leaseSignedDate = lease.leaseSignedDate != null ? moment(lease.leaseSignedDate) : null;
                lease.leaseStartDate = lease.leaseStartDate != null ? moment(lease.leaseStartDate) : null;
                lease.leaseEndDate = lease.leaseEndDate != null ? moment(lease.leaseEndDate) : null;
                lease.proRataEndDate = lease.proRataEndDate != null ? moment(lease.proRataEndDate) : null;
            });
        }
        return res;
    }
}
