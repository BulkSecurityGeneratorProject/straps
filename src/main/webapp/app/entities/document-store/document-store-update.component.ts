import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from './document-store.service';

@Component({
    selector: 'jhi-document-store-update',
    templateUrl: './document-store-update.component.html'
})
export class DocumentStoreUpdateComponent implements OnInit {
    documentStore: IDocumentStore;
    isSaving: boolean;

    constructor(protected documentStoreService: DocumentStoreService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentStore }) => {
            this.documentStore = documentStore;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentStore.id !== undefined) {
            this.subscribeToSaveResponse(this.documentStoreService.update(this.documentStore));
        } else {
            this.subscribeToSaveResponse(this.documentStoreService.create(this.documentStore));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentStore>>) {
        result.subscribe((res: HttpResponse<IDocumentStore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
