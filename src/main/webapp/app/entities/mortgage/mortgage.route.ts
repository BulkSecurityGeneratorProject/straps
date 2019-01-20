import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mortgage } from 'app/shared/model/mortgage.model';
import { MortgageService } from './mortgage.service';
import { MortgageComponent } from './mortgage.component';
import { MortgageDetailComponent } from './mortgage-detail.component';
import { MortgageUpdateComponent } from './mortgage-update.component';
import { MortgageDeletePopupComponent } from './mortgage-delete-dialog.component';
import { IMortgage } from 'app/shared/model/mortgage.model';

@Injectable({ providedIn: 'root' })
export class MortgageResolve implements Resolve<IMortgage> {
    constructor(private service: MortgageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Mortgage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Mortgage>) => response.ok),
                map((mortgage: HttpResponse<Mortgage>) => mortgage.body)
            );
        }
        return of(new Mortgage());
    }
}

export const mortgageRoute: Routes = [
    {
        path: 'mortgage',
        component: MortgageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.mortgage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mortgage/:id/view',
        component: MortgageDetailComponent,
        resolve: {
            mortgage: MortgageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.mortgage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mortgage/new',
        component: MortgageUpdateComponent,
        resolve: {
            mortgage: MortgageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.mortgage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mortgage/:id/edit',
        component: MortgageUpdateComponent,
        resolve: {
            mortgage: MortgageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.mortgage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mortgagePopupRoute: Routes = [
    {
        path: 'mortgage/:id/delete',
        component: MortgageDeletePopupComponent,
        resolve: {
            mortgage: MortgageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.mortgage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
