import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Amenity } from 'app/shared/model/amenity.model';
import { AmenityService } from './amenity.service';
import { AmenityComponent } from './amenity.component';
import { AmenityDetailComponent } from './amenity-detail.component';
import { AmenityUpdateComponent } from './amenity-update.component';
import { AmenityDeletePopupComponent } from './amenity-delete-dialog.component';
import { IAmenity } from 'app/shared/model/amenity.model';

@Injectable({ providedIn: 'root' })
export class AmenityResolve implements Resolve<IAmenity> {
    constructor(private service: AmenityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Amenity> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Amenity>) => response.ok),
                map((amenity: HttpResponse<Amenity>) => amenity.body)
            );
        }
        return of(new Amenity());
    }
}

export const amenityRoute: Routes = [
    {
        path: 'amenity',
        component: AmenityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.amenity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amenity/:id/view',
        component: AmenityDetailComponent,
        resolve: {
            amenity: AmenityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.amenity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amenity/new',
        component: AmenityUpdateComponent,
        resolve: {
            amenity: AmenityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.amenity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amenity/:id/edit',
        component: AmenityUpdateComponent,
        resolve: {
            amenity: AmenityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.amenity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amenityPopupRoute: Routes = [
    {
        path: 'amenity/:id/delete',
        component: AmenityDeletePopupComponent,
        resolve: {
            amenity: AmenityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.amenity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
