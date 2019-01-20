import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Contract } from 'app/shared/model/contract.model';
import { ContractService } from './contract.service';
import { ContractComponent } from './contract.component';
import { ContractDetailComponent } from './contract-detail.component';
import { ContractUpdateComponent } from './contract-update.component';
import { ContractDeletePopupComponent } from './contract-delete-dialog.component';
import { IContract } from 'app/shared/model/contract.model';

@Injectable({ providedIn: 'root' })
export class ContractResolve implements Resolve<IContract> {
    constructor(private service: ContractService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Contract> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Contract>) => response.ok),
                map((contract: HttpResponse<Contract>) => contract.body)
            );
        }
        return of(new Contract());
    }
}

export const contractRoute: Routes = [
    {
        path: 'contract',
        component: ContractComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.contract.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contract/:id/view',
        component: ContractDetailComponent,
        resolve: {
            contract: ContractResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.contract.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contract/new',
        component: ContractUpdateComponent,
        resolve: {
            contract: ContractResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.contract.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contract/:id/edit',
        component: ContractUpdateComponent,
        resolve: {
            contract: ContractResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.contract.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contractPopupRoute: Routes = [
    {
        path: 'contract/:id/delete',
        component: ContractDeletePopupComponent,
        resolve: {
            contract: ContractResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.contract.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
