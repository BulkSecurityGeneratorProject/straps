import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanyCapability } from 'app/shared/model/company-capability.model';
import { CompanyCapabilityService } from './company-capability.service';
import { CompanyCapabilityComponent } from './company-capability.component';
import { CompanyCapabilityDetailComponent } from './company-capability-detail.component';
import { CompanyCapabilityUpdateComponent } from './company-capability-update.component';
import { CompanyCapabilityDeletePopupComponent } from './company-capability-delete-dialog.component';
import { ICompanyCapability } from 'app/shared/model/company-capability.model';

@Injectable({ providedIn: 'root' })
export class CompanyCapabilityResolve implements Resolve<ICompanyCapability> {
    constructor(private service: CompanyCapabilityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CompanyCapability> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CompanyCapability>) => response.ok),
                map((companyCapability: HttpResponse<CompanyCapability>) => companyCapability.body)
            );
        }
        return of(new CompanyCapability());
    }
}

export const companyCapabilityRoute: Routes = [
    {
        path: 'company-capability',
        component: CompanyCapabilityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.companyCapability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'company-capability/:id/view',
        component: CompanyCapabilityDetailComponent,
        resolve: {
            companyCapability: CompanyCapabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.companyCapability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'company-capability/new',
        component: CompanyCapabilityUpdateComponent,
        resolve: {
            companyCapability: CompanyCapabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.companyCapability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'company-capability/:id/edit',
        component: CompanyCapabilityUpdateComponent,
        resolve: {
            companyCapability: CompanyCapabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.companyCapability.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyCapabilityPopupRoute: Routes = [
    {
        path: 'company-capability/:id/delete',
        component: CompanyCapabilityDeletePopupComponent,
        resolve: {
            companyCapability: CompanyCapabilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.companyCapability.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
