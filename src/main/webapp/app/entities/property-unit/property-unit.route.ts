import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from './property-unit.service';
import { PropertyUnitComponent } from './property-unit.component';
import { PropertyUnitDetailComponent } from './property-unit-detail.component';
import { PropertyUnitUpdateComponent } from './property-unit-update.component';
import { PropertyUnitDeletePopupComponent } from './property-unit-delete-dialog.component';
import { IPropertyUnit } from 'app/shared/model/property-unit.model';

@Injectable({ providedIn: 'root' })
export class PropertyUnitResolve implements Resolve<IPropertyUnit> {
    constructor(private service: PropertyUnitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PropertyUnit> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PropertyUnit>) => response.ok),
                map((propertyUnit: HttpResponse<PropertyUnit>) => propertyUnit.body)
            );
        }
        return of(new PropertyUnit());
    }
}

export const propertyUnitRoute: Routes = [
    {
        path: 'property-unit',
        component: PropertyUnitComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.propertyUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-unit/:id/view',
        component: PropertyUnitDetailComponent,
        resolve: {
            propertyUnit: PropertyUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-unit/new',
        component: PropertyUnitUpdateComponent,
        resolve: {
            propertyUnit: PropertyUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'property-unit/:id/edit',
        component: PropertyUnitUpdateComponent,
        resolve: {
            propertyUnit: PropertyUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const propertyUnitPopupRoute: Routes = [
    {
        path: 'property-unit/:id/delete',
        component: PropertyUnitDeletePopupComponent,
        resolve: {
            propertyUnit: PropertyUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.propertyUnit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
