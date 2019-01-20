import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from './document-store.service';
import { DocumentStoreComponent } from './document-store.component';
import { DocumentStoreDetailComponent } from './document-store-detail.component';
import { DocumentStoreUpdateComponent } from './document-store-update.component';
import { DocumentStoreDeletePopupComponent } from './document-store-delete-dialog.component';
import { IDocumentStore } from 'app/shared/model/document-store.model';

@Injectable({ providedIn: 'root' })
export class DocumentStoreResolve implements Resolve<IDocumentStore> {
    constructor(private service: DocumentStoreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DocumentStore> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DocumentStore>) => response.ok),
                map((documentStore: HttpResponse<DocumentStore>) => documentStore.body)
            );
        }
        return of(new DocumentStore());
    }
}

export const documentStoreRoute: Routes = [
    {
        path: 'document-store',
        component: DocumentStoreComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.documentStore.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-store/:id/view',
        component: DocumentStoreDetailComponent,
        resolve: {
            documentStore: DocumentStoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.documentStore.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-store/new',
        component: DocumentStoreUpdateComponent,
        resolve: {
            documentStore: DocumentStoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.documentStore.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-store/:id/edit',
        component: DocumentStoreUpdateComponent,
        resolve: {
            documentStore: DocumentStoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.documentStore.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentStorePopupRoute: Routes = [
    {
        path: 'document-store/:id/delete',
        component: DocumentStoreDeletePopupComponent,
        resolve: {
            documentStore: DocumentStoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.documentStore.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
