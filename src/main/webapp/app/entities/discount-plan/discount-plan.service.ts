import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiscountPlan } from 'app/shared/model/discount-plan.model';

type EntityResponseType = HttpResponse<IDiscountPlan>;
type EntityArrayResponseType = HttpResponse<IDiscountPlan[]>;

@Injectable({ providedIn: 'root' })
export class DiscountPlanService {
    public resourceUrl = SERVER_API_URL + 'api/discount-plans';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/discount-plans';

    constructor(protected http: HttpClient) {}

    create(discountPlan: IDiscountPlan): Observable<EntityResponseType> {
        return this.http.post<IDiscountPlan>(this.resourceUrl, discountPlan, { observe: 'response' });
    }

    update(discountPlan: IDiscountPlan): Observable<EntityResponseType> {
        return this.http.put<IDiscountPlan>(this.resourceUrl, discountPlan, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDiscountPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDiscountPlan[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDiscountPlan[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
