import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Notes } from 'app/shared/model/notes.model';
import { NotesService } from './notes.service';
import { NotesComponent } from './notes.component';
import { NotesDetailComponent } from './notes-detail.component';
import { NotesUpdateComponent } from './notes-update.component';
import { NotesDeletePopupComponent } from './notes-delete-dialog.component';
import { INotes } from 'app/shared/model/notes.model';

@Injectable({ providedIn: 'root' })
export class NotesResolve implements Resolve<INotes> {
    constructor(private service: NotesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Notes> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Notes>) => response.ok),
                map((notes: HttpResponse<Notes>) => notes.body)
            );
        }
        return of(new Notes());
    }
}

export const notesRoute: Routes = [
    {
        path: 'notes',
        component: NotesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'promalyV5App.notes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notes/:id/view',
        component: NotesDetailComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.notes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notes/new',
        component: NotesUpdateComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.notes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notes/:id/edit',
        component: NotesUpdateComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.notes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notesPopupRoute: Routes = [
    {
        path: 'notes/:id/delete',
        component: NotesDeletePopupComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'promalyV5App.notes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
