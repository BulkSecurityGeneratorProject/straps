import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from './document-store.service';

@Component({
    selector: 'jhi-document-store-delete-dialog',
    templateUrl: './document-store-delete-dialog.component.html'
})
export class DocumentStoreDeleteDialogComponent {
    documentStore: IDocumentStore;

    constructor(
        protected documentStoreService: DocumentStoreService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentStoreService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentStoreListModification',
                content: 'Deleted an documentStore'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-store-delete-popup',
    template: ''
})
export class DocumentStoreDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentStore }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DocumentStoreDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.documentStore = documentStore;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
