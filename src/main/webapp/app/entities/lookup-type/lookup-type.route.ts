import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LookupType } from 'app/shared/model/lookup-type.model';
import { LookupTypeService } from './lookup-type.service';
import { LookupTypeComponent } from './lookup-type.component';
import { LookupTypeDetailComponent } from './lookup-type-detail.component';
import { LookupTypeUpdateComponent } from './lookup-type-update.component';
import { LookupTypeDeletePopupComponent } from './lookup-type-delete-dialog.component';
import { ILookupType } from 'app/shared/model/lookup-type.model';

@Injectable({ providedIn: 'root' })
export class LookupTypeResolve implements Resolve<ILookupType> {
    constructor(private service: LookupTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<LookupType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LookupType>) => response.ok),
                map((lookupType: HttpResponse<LookupType>) => lookupType.body)
            );
        }
        return of(new LookupType());
    }
}

export const lookupTypeRoute: Routes = [
    {
        path: 'lookup-type',
        component: LookupTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.lookupType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lookup-type/:id/view',
        component: LookupTypeDetailComponent,
        resolve: {
            lookupType: LookupTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookupType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lookup-type/new',
        component: LookupTypeUpdateComponent,
        resolve: {
            lookupType: LookupTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookupType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lookup-type/:id/edit',
        component: LookupTypeUpdateComponent,
        resolve: {
            lookupType: LookupTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookupType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lookupTypePopupRoute: Routes = [
    {
        path: 'lookup-type/:id/delete',
        component: LookupTypeDeletePopupComponent,
        resolve: {
            lookupType: LookupTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookupType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
