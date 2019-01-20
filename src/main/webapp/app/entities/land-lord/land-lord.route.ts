import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LandLord } from 'app/shared/model/land-lord.model';
import { LandLordService } from './land-lord.service';
import { LandLordComponent } from './land-lord.component';
import { LandLordDetailComponent } from './land-lord-detail.component';
import { LandLordUpdateComponent } from './land-lord-update.component';
import { LandLordDeletePopupComponent } from './land-lord-delete-dialog.component';
import { ILandLord } from 'app/shared/model/land-lord.model';

@Injectable({ providedIn: 'root' })
export class LandLordResolve implements Resolve<ILandLord> {
    constructor(private service: LandLordService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<LandLord> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LandLord>) => response.ok),
                map((landLord: HttpResponse<LandLord>) => landLord.body)
            );
        }
        return of(new LandLord());
    }
}

export const landLordRoute: Routes = [
    {
        path: 'land-lord',
        component: LandLordComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.landLord.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'land-lord/:id/view',
        component: LandLordDetailComponent,
        resolve: {
            landLord: LandLordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.landLord.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'land-lord/new',
        component: LandLordUpdateComponent,
        resolve: {
            landLord: LandLordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.landLord.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'land-lord/:id/edit',
        component: LandLordUpdateComponent,
        resolve: {
            landLord: LandLordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.landLord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const landLordPopupRoute: Routes = [
    {
        path: 'land-lord/:id/delete',
        component: LandLordDeletePopupComponent,
        resolve: {
            landLord: LandLordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.landLord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
