import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Feature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';
import { FeatureComponent } from './feature.component';
import { FeatureDetailComponent } from './feature-detail.component';
import { FeatureUpdateComponent } from './feature-update.component';
import { FeatureDeletePopupComponent } from './feature-delete-dialog.component';
import { IFeature } from 'app/shared/model/feature.model';

@Injectable({ providedIn: 'root' })
export class FeatureResolve implements Resolve<IFeature> {
    constructor(private service: FeatureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Feature> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Feature>) => response.ok),
                map((feature: HttpResponse<Feature>) => feature.body)
            );
        }
        return of(new Feature());
    }
}

export const featureRoute: Routes = [
    {
        path: 'feature',
        component: FeatureComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.feature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feature/:id/view',
        component: FeatureDetailComponent,
        resolve: {
            feature: FeatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.feature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feature/new',
        component: FeatureUpdateComponent,
        resolve: {
            feature: FeatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.feature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feature/:id/edit',
        component: FeatureUpdateComponent,
        resolve: {
            feature: FeatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.feature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const featurePopupRoute: Routes = [
    {
        path: 'feature/:id/delete',
        component: FeatureDeletePopupComponent,
        resolve: {
            feature: FeatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.feature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
