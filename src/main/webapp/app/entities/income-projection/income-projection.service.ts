import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncomeProjection } from 'app/shared/model/income-projection.model';

type EntityResponseType = HttpResponse<IIncomeProjection>;
type EntityArrayResponseType = HttpResponse<IIncomeProjection[]>;

@Injectable({ providedIn: 'root' })
export class IncomeProjectionService {
    public resourceUrl = SERVER_API_URL + 'api/income-projections';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/income-projections';

    constructor(protected http: HttpClient) {}

    create(incomeProjection: IIncomeProjection): Observable<EntityResponseType> {
        return this.http.post<IIncomeProjection>(this.resourceUrl, incomeProjection, { observe: 'response' });
    }

    update(incomeProjection: IIncomeProjection): Observable<EntityResponseType> {
        return this.http.put<IIncomeProjection>(this.resourceUrl, incomeProjection, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIncomeProjection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIncomeProjection[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIncomeProjection[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
