import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentStore } from 'app/shared/model/document-store.model';

type EntityResponseType = HttpResponse<IDocumentStore>;
type EntityArrayResponseType = HttpResponse<IDocumentStore[]>;

@Injectable({ providedIn: 'root' })
export class DocumentStoreService {
    public resourceUrl = SERVER_API_URL + 'api/document-stores';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/document-stores';

    constructor(protected http: HttpClient) {}

    create(documentStore: IDocumentStore): Observable<EntityResponseType> {
        return this.http.post<IDocumentStore>(this.resourceUrl, documentStore, { observe: 'response' });
    }

    update(documentStore: IDocumentStore): Observable<EntityResponseType> {
        return this.http.put<IDocumentStore>(this.resourceUrl, documentStore, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocumentStore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentStore[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentStore[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
