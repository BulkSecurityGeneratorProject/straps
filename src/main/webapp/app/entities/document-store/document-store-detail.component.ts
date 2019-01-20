import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentStore } from 'app/shared/model/document-store.model';

@Component({
    selector: 'jhi-document-store-detail',
    templateUrl: './document-store-detail.component.html'
})
export class DocumentStoreDetailComponent implements OnInit {
    documentStore: IDocumentStore;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentStore }) => {
            this.documentStore = documentStore;
        });
    }

    previousState() {
        window.history.back();
    }
}
