import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DiscountPlan } from 'app/shared/model/discount-plan.model';
import { DiscountPlanService } from './discount-plan.service';
import { DiscountPlanComponent } from './discount-plan.component';
import { DiscountPlanDetailComponent } from './discount-plan-detail.component';
import { DiscountPlanUpdateComponent } from './discount-plan-update.component';
import { DiscountPlanDeletePopupComponent } from './discount-plan-delete-dialog.component';
import { IDiscountPlan } from 'app/shared/model/discount-plan.model';

@Injectable({ providedIn: 'root' })
export class DiscountPlanResolve implements Resolve<IDiscountPlan> {
    constructor(private service: DiscountPlanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DiscountPlan> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DiscountPlan>) => response.ok),
                map((discountPlan: HttpResponse<DiscountPlan>) => discountPlan.body)
            );
        }
        return of(new DiscountPlan());
    }
}

export const discountPlanRoute: Routes = [
    {
        path: 'discount-plan',
        component: DiscountPlanComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.discountPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'discount-plan/:id/view',
        component: DiscountPlanDetailComponent,
        resolve: {
            discountPlan: DiscountPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.discountPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'discount-plan/new',
        component: DiscountPlanUpdateComponent,
        resolve: {
            discountPlan: DiscountPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.discountPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'discount-plan/:id/edit',
        component: DiscountPlanUpdateComponent,
        resolve: {
            discountPlan: DiscountPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.discountPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const discountPlanPopupRoute: Routes = [
    {
        path: 'discount-plan/:id/delete',
        component: DiscountPlanDeletePopupComponent,
        resolve: {
            discountPlan: DiscountPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.discountPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
