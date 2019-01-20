import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PropertyVendor } from 'app/shared/model/property-vendor.model';
import { PropertyVendorService } from './property-vendor.service';
import { PropertyVendorComponent } from './property-vendor.component';
import { PropertyVendorDetailComponent } from './property-vendor-detail.component';
import { PropertyVendorUpdateComponent } from './property-vendor-update.component';
import { PropertyVendorDeletePopupComponent } from './property-vendor-delete-dialog.component';
import { IPropertyVendor } from 'app/shared/model/property-vendor.model';

@Injectable({ providedIn: 'root' })
export class PropertyVendorResolve implements Resolve<IPropertyVendor> {
    constructor(private service: PropertyVendorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PropertyVendor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PropertyVendor>) => response.ok),
                map((propertyVendor: HttpResponse<PropertyVendor>) => propertyVendor.body)
            );
        }
        return of(new PropertyVendor());
    }
}

export const propertyVendorRoute: Routes = [
    {
        path: 'property-vendor',
        component: PropertyVendorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.propertyVendor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-vendor/:id/view',
        component: PropertyVendorDetailComponent,
        resolve: {
            propertyVendor: PropertyVendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyVendor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-vendor/new',
        component: PropertyVendorUpdateComponent,
        resolve: {
            propertyVendor: PropertyVendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyVendor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-vendor/:id/edit',
        component: PropertyVendorUpdateComponent,
        resolve: {
            propertyVendor: PropertyVendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyVendor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const propertyVendorPopupRoute: Routes = [
    {
        path: 'property-vendor/:id/delete',
        component: PropertyVendorDeletePopupComponent,
        resolve: {
            propertyVendor: PropertyVendorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyVendor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
