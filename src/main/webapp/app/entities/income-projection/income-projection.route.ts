import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IncomeProjection } from 'app/shared/model/income-projection.model';
import { IncomeProjectionService } from './income-projection.service';
import { IncomeProjectionComponent } from './income-projection.component';
import { IncomeProjectionDetailComponent } from './income-projection-detail.component';
import { IncomeProjectionUpdateComponent } from './income-projection-update.component';
import { IncomeProjectionDeletePopupComponent } from './income-projection-delete-dialog.component';
import { IIncomeProjection } from 'app/shared/model/income-projection.model';

@Injectable({ providedIn: 'root' })
export class IncomeProjectionResolve implements Resolve<IIncomeProjection> {
    constructor(private service: IncomeProjectionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IncomeProjection> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IncomeProjection>) => response.ok),
                map((incomeProjection: HttpResponse<IncomeProjection>) => incomeProjection.body)
            );
        }
        return of(new IncomeProjection());
    }
}

export const incomeProjectionRoute: Routes = [
    {
        path: 'income-projection',
        component: IncomeProjectionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.incomeProjection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'income-projection/:id/view',
        component: IncomeProjectionDetailComponent,
        resolve: {
            incomeProjection: IncomeProjectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.incomeProjection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'income-projection/new',
        component: IncomeProjectionUpdateComponent,
        resolve: {
            incomeProjection: IncomeProjectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.incomeProjection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'income-projection/:id/edit',
        component: IncomeProjectionUpdateComponent,
        resolve: {
            incomeProjection: IncomeProjectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.incomeProjection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incomeProjectionPopupRoute: Routes = [
    {
        path: 'income-projection/:id/delete',
        component: IncomeProjectionDeletePopupComponent,
        resolve: {
            incomeProjection: IncomeProjectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.incomeProjection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
