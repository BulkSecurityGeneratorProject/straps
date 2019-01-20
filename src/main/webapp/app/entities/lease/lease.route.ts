import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lease } from 'app/shared/model/lease.model';
import { LeaseService } from './lease.service';
import { LeaseComponent } from './lease.component';
import { LeaseDetailComponent } from './lease-detail.component';
import { LeaseUpdateComponent } from './lease-update.component';
import { LeaseDeletePopupComponent } from './lease-delete-dialog.component';
import { ILease } from 'app/shared/model/lease.model';

@Injectable({ providedIn: 'root' })
export class LeaseResolve implements Resolve<ILease> {
    constructor(private service: LeaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lease> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lease>) => response.ok),
                map((lease: HttpResponse<Lease>) => lease.body)
            );
        }
        return of(new Lease());
    }
}

export const leaseRoute: Routes = [
    {
        path: 'lease',
        component: LeaseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.lease.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lease/:id/view',
        component: LeaseDetailComponent,
        resolve: {
            lease: LeaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lease.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lease/new',
        component: LeaseUpdateComponent,
        resolve: {
            lease: LeaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lease.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lease/:id/edit',
        component: LeaseUpdateComponent,
        resolve: {
            lease: LeaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lease.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leasePopupRoute: Routes = [
    {
        path: 'lease/:id/delete',
        component: LeaseDeletePopupComponent,
        resolve: {
            lease: LeaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.lease.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
