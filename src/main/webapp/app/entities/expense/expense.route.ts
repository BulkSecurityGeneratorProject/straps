import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Expense } from 'app/shared/model/expense.model';
import { ExpenseService } from './expense.service';
import { ExpenseComponent } from './expense.component';
import { ExpenseDetailComponent } from './expense-detail.component';
import { ExpenseUpdateComponent } from './expense-update.component';
import { ExpenseDeletePopupComponent } from './expense-delete-dialog.component';
import { IExpense } from 'app/shared/model/expense.model';

@Injectable({ providedIn: 'root' })
export class ExpenseResolve implements Resolve<IExpense> {
    constructor(private service: ExpenseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Expense> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Expense>) => response.ok),
                map((expense: HttpResponse<Expense>) => expense.body)
            );
        }
        return of(new Expense());
    }
}

export const expenseRoute: Routes = [
    {
        path: 'expense',
        component: ExpenseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.expense.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'expense/:id/view',
        component: ExpenseDetailComponent,
        resolve: {
            expense: ExpenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.expense.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'expense/new',
        component: ExpenseUpdateComponent,
        resolve: {
            expense: ExpenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.expense.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'expense/:id/edit',
        component: ExpenseUpdateComponent,
        resolve: {
            expense: ExpenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.expense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const expensePopupRoute: Routes = [
    {
        path: 'expense/:id/delete',
        component: ExpenseDeletePopupComponent,
        resolve: {
            expense: ExpenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.expense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
