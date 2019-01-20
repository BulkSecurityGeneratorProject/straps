import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILandLord } from 'app/shared/model/land-lord.model';

type EntityResponseType = HttpResponse<ILandLord>;
type EntityArrayResponseType = HttpResponse<ILandLord[]>;

@Injectable({ providedIn: 'root' })
export class LandLordService {
    public resourceUrl = SERVER_API_URL + 'api/land-lords';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/land-lords';

    constructor(protected http: HttpClient) {}

    create(landLord: ILandLord): Observable<EntityResponseType> {
        return this.http.post<ILandLord>(this.resourceUrl, landLord, { observe: 'response' });
    }

    update(landLord: ILandLord): Observable<EntityResponseType> {
        return this.http.put<ILandLord>(this.resourceUrl, landLord, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILandLord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILandLord[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILandLord[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
