import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBuilding } from 'app/shared/model/building.model';

type EntityResponseType = HttpResponse<IBuilding>;
type EntityArrayResponseType = HttpResponse<IBuilding[]>;

@Injectable({ providedIn: 'root' })
export class BuildingService {
    public resourceUrl = SERVER_API_URL + 'api/buildings';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/buildings';

    constructor(protected http: HttpClient) {}

    create(building: IBuilding): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(building);
        return this.http
            .post<IBuilding>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(building: IBuilding): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(building);
        return this.http
            .put<IBuilding>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBuilding>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBuilding[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBuilding[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(building: IBuilding): IBuilding {
        const copy: IBuilding = Object.assign({}, building, {
            yearBuilt: building.yearBuilt != null && building.yearBuilt.isValid() ? building.yearBuilt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.yearBuilt = res.body.yearBuilt != null ? moment(res.body.yearBuilt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((building: IBuilding) => {
                building.yearBuilt = building.yearBuilt != null ? moment(building.yearBuilt) : null;
            });
        }
        return res;
    }
}
