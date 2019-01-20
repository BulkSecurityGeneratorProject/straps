import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RentRoll } from 'app/shared/model/rent-roll.model';
import { RentRollService } from './rent-roll.service';
import { RentRollComponent } from './rent-roll.component';
import { RentRollDetailComponent } from './rent-roll-detail.component';
import { RentRollUpdateComponent } from './rent-roll-update.component';
import { RentRollDeletePopupComponent } from './rent-roll-delete-dialog.component';
import { IRentRoll } from 'app/shared/model/rent-roll.model';

@Injectable({ providedIn: 'root' })
export class RentRollResolve implements Resolve<IRentRoll> {
    constructor(private service: RentRollService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RentRoll> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RentRoll>) => response.ok),
                map((rentRoll: HttpResponse<RentRoll>) => rentRoll.body)
            );
        }
        return of(new RentRoll());
    }
}

export const rentRollRoute: Routes = [
    {
        path: 'rent-roll',
        component: RentRollComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.rentRoll.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rent-roll/:id/view',
        component: RentRollDetailComponent,
        resolve: {
            rentRoll: RentRollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.rentRoll.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rent-roll/new',
        component: RentRollUpdateComponent,
        resolve: {
            rentRoll: RentRollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.rentRoll.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rent-roll/:id/edit',
        component: RentRollUpdateComponent,
        resolve: {
            rentRoll: RentRollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.rentRoll.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rentRollPopupRoute: Routes = [
    {
        path: 'rent-roll/:id/delete',
        component: RentRollDeletePopupComponent,
        resolve: {
            rentRoll: RentRollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.rentRoll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
