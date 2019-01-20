import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBillingPlan } from 'app/shared/model/billing-plan.model';

type EntityResponseType = HttpResponse<IBillingPlan>;
type EntityArrayResponseType = HttpResponse<IBillingPlan[]>;

@Injectable({ providedIn: 'root' })
export class BillingPlanService {
    public resourceUrl = SERVER_API_URL + 'api/billing-plans';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/billing-plans';

    constructor(protected http: HttpClient) {}

    create(billingPlan: IBillingPlan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billingPlan);
        return this.http
            .post<IBillingPlan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(billingPlan: IBillingPlan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billingPlan);
        return this.http
            .put<IBillingPlan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBillingPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBillingPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBillingPlan[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(billingPlan: IBillingPlan): IBillingPlan {
        const copy: IBillingPlan = Object.assign({}, billingPlan, {
            effectiveDate:
                billingPlan.effectiveDate != null && billingPlan.effectiveDate.isValid()
                    ? billingPlan.effectiveDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.effectiveDate = res.body.effectiveDate != null ? moment(res.body.effectiveDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((billingPlan: IBillingPlan) => {
                billingPlan.effectiveDate = billingPlan.effectiveDate != null ? moment(billingPlan.effectiveDate) : null;
            });
        }
        return res;
    }
}
