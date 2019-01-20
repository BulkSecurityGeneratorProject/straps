import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Portfolio } from 'app/shared/model/portfolio.model';
import { PortfolioService } from './portfolio.service';
import { PortfolioComponent } from './portfolio.component';
import { PortfolioDetailComponent } from './portfolio-detail.component';
import { PortfolioUpdateComponent } from './portfolio-update.component';
import { PortfolioDeletePopupComponent } from './portfolio-delete-dialog.component';
import { IPortfolio } from 'app/shared/model/portfolio.model';

@Injectable({ providedIn: 'root' })
export class PortfolioResolve implements Resolve<IPortfolio> {
    constructor(private service: PortfolioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Portfolio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Portfolio>) => response.ok),
                map((portfolio: HttpResponse<Portfolio>) => portfolio.body)
            );
        }
        return of(new Portfolio());
    }
}

export const portfolioRoute: Routes = [
    {
        path: 'portfolio',
        component: PortfolioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.portfolio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portfolio/:id/view',
        component: PortfolioDetailComponent,
        resolve: {
            portfolio: PortfolioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.portfolio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portfolio/new',
        component: PortfolioUpdateComponent,
        resolve: {
            portfolio: PortfolioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.portfolio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portfolio/:id/edit',
        component: PortfolioUpdateComponent,
        resolve: {
            portfolio: PortfolioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.portfolio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const portfolioPopupRoute: Routes = [
    {
        path: 'portfolio/:id/delete',
        component: PortfolioDeletePopupComponent,
        resolve: {
            portfolio: PortfolioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.portfolio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
