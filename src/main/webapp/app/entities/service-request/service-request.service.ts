import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceRequest } from 'app/shared/model/service-request.model';

type EntityResponseType = HttpResponse<IServiceRequest>;
type EntityArrayResponseType = HttpResponse<IServiceRequest[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRequestService {
    public resourceUrl = SERVER_API_URL + 'api/service-requests';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/service-requests';

    constructor(protected http: HttpClient) {}

    create(serviceRequest: IServiceRequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(serviceRequest);
        return this.http
            .post<IServiceRequest>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(serviceRequest: IServiceRequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(serviceRequest);
        return this.http
            .put<IServiceRequest>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IServiceRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IServiceRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IServiceRequest[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(serviceRequest: IServiceRequest): IServiceRequest {
        const copy: IServiceRequest = Object.assign({}, serviceRequest, {
            requestDateTime:
                serviceRequest.requestDateTime != null && serviceRequest.requestDateTime.isValid()
                    ? serviceRequest.requestDateTime.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.requestDateTime = res.body.requestDateTime != null ? moment(res.body.requestDateTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((serviceRequest: IServiceRequest) => {
                serviceRequest.requestDateTime = serviceRequest.requestDateTime != null ? moment(serviceRequest.requestDateTime) : null;
            });
        }
        return res;
    }
}
