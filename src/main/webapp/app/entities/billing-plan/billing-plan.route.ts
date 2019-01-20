import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BillingPlan } from 'app/shared/model/billing-plan.model';
import { BillingPlanService } from './billing-plan.service';
import { BillingPlanComponent } from './billing-plan.component';
import { BillingPlanDetailComponent } from './billing-plan-detail.component';
import { BillingPlanUpdateComponent } from './billing-plan-update.component';
import { BillingPlanDeletePopupComponent } from './billing-plan-delete-dialog.component';
import { IBillingPlan } from 'app/shared/model/billing-plan.model';

@Injectable({ providedIn: 'root' })
export class BillingPlanResolve implements Resolve<IBillingPlan> {
    constructor(private service: BillingPlanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BillingPlan> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BillingPlan>) => response.ok),
                map((billingPlan: HttpResponse<BillingPlan>) => billingPlan.body)
            );
        }
        return of(new BillingPlan());
    }
}

export const billingPlanRoute: Routes = [
    {
        path: 'billing-plan',
        component: BillingPlanComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.billingPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing-plan/:id/view',
        component: BillingPlanDetailComponent,
        resolve: {
            billingPlan: BillingPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.billingPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing-plan/new',
        component: BillingPlanUpdateComponent,
        resolve: {
            billingPlan: BillingPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.billingPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing-plan/:id/edit',
        component: BillingPlanUpdateComponent,
        resolve: {
            billingPlan: BillingPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.billingPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billingPlanPopupRoute: Routes = [
    {
        path: 'billing-plan/:id/delete',
        component: BillingPlanDeletePopupComponent,
        resolve: {
            billingPlan: BillingPlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.billingPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
