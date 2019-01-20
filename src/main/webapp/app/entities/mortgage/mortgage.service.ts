import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMortgage } from 'app/shared/model/mortgage.model';

type EntityResponseType = HttpResponse<IMortgage>;
type EntityArrayResponseType = HttpResponse<IMortgage[]>;

@Injectable({ providedIn: 'root' })
export class MortgageService {
    public resourceUrl = SERVER_API_URL + 'api/mortgages';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/mortgages';

    constructor(protected http: HttpClient) {}

    create(mortgage: IMortgage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mortgage);
        return this.http
            .post<IMortgage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(mortgage: IMortgage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mortgage);
        return this.http
            .put<IMortgage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMortgage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMortgage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMortgage[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(mortgage: IMortgage): IMortgage {
        const copy: IMortgage = Object.assign({}, mortgage, {
            startDate: mortgage.startDate != null && mortgage.startDate.isValid() ? mortgage.startDate.format(DATE_FORMAT) : null,
            endDate: mortgage.endDate != null && mortgage.endDate.isValid() ? mortgage.endDate.format(DATE_FORMAT) : null
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
            res.body.forEach((mortgage: IMortgage) => {
                mortgage.startDate = mortgage.startDate != null ? moment(mortgage.startDate) : null;
                mortgage.endDate = mortgage.endDate != null ? moment(mortgage.endDate) : null;
            });
        }
        return res;
    }
}
