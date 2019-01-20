import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanyCapability } from 'app/shared/model/company-capability.model';

type EntityResponseType = HttpResponse<ICompanyCapability>;
type EntityArrayResponseType = HttpResponse<ICompanyCapability[]>;

@Injectable({ providedIn: 'root' })
export class CompanyCapabilityService {
    public resourceUrl = SERVER_API_URL + 'api/company-capabilities';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/company-capabilities';

    constructor(protected http: HttpClient) {}

    create(companyCapability: ICompanyCapability): Observable<EntityResponseType> {
        return this.http.post<ICompanyCapability>(this.resourceUrl, companyCapability, { observe: 'response' });
    }

    update(companyCapability: ICompanyCapability): Observable<EntityResponseType> {
        return this.http.put<ICompanyCapability>(this.resourceUrl, companyCapability, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICompanyCapability>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompanyCapability[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompanyCapability[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
