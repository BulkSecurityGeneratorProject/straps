import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Appliance } from 'app/shared/model/appliance.model';
import { ApplianceService } from './appliance.service';
import { ApplianceComponent } from './appliance.component';
import { ApplianceDetailComponent } from './appliance-detail.component';
import { ApplianceUpdateComponent } from './appliance-update.component';
import { ApplianceDeletePopupComponent } from './appliance-delete-dialog.component';
import { IAppliance } from 'app/shared/model/appliance.model';

@Injectable({ providedIn: 'root' })
export class ApplianceResolve implements Resolve<IAppliance> {
    constructor(private service: ApplianceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Appliance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Appliance>) => response.ok),
                map((appliance: HttpResponse<Appliance>) => appliance.body)
            );
        }
        return of(new Appliance());
    }
}

export const applianceRoute: Routes = [
    {
        path: 'appliance',
        component: ApplianceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.appliance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'appliance/:id/view',
        component: ApplianceDetailComponent,
        resolve: {
            appliance: ApplianceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.appliance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'appliance/new',
        component: ApplianceUpdateComponent,
        resolve: {
            appliance: ApplianceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.appliance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'appliance/:id/edit',
        component: ApplianceUpdateComponent,
        resolve: {
            appliance: ApplianceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.appliance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appliancePopupRoute: Routes = [
    {
        path: 'appliance/:id/delete',
        component: ApplianceDeletePopupComponent,
        resolve: {
            appliance: ApplianceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.appliance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
