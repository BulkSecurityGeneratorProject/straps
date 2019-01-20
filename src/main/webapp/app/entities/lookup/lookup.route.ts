import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lookup } from 'app/shared/model/lookup.model';
import { LookupService } from './lookup.service';
import { LookupComponent } from './lookup.component';
import { LookupDetailComponent } from './lookup-detail.component';
import { LookupUpdateComponent } from './lookup-update.component';
import { LookupDeletePopupComponent } from './lookup-delete-dialog.component';
import { ILookup } from 'app/shared/model/lookup.model';

@Injectable({ providedIn: 'root' })
export class LookupResolve implements Resolve<ILookup> {
    constructor(private service: LookupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lookup> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lookup>) => response.ok),
                map((lookup: HttpResponse<Lookup>) => lookup.body)
            );
        }
        return of(new Lookup());
    }
}

export const lookupRoute: Routes = [
    {
        path: 'lookup',
        component: LookupComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.lookup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lookup/:id/view',
        component: LookupDetailComponent,
        resolve: {
            lookup: LookupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lookup/new',
        component: LookupUpdateComponent,
        resolve: {
            lookup: LookupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lookup/:id/edit',
        component: LookupUpdateComponent,
        resolve: {
            lookup: LookupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lookupPopupRoute: Routes = [
    {
        path: 'lookup/:id/delete',
        component: LookupDeletePopupComponent,
        resolve: {
            lookup: LookupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lookup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
