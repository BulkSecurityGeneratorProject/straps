import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILookupType } from 'app/shared/model/lookup-type.model';
import { LookupTypeService } from './lookup-type.service';

@Component({
    selector: 'jhi-lookup-type-delete-dialog',
    templateUrl: './lookup-type-delete-dialog.component.html'
})
export class LookupTypeDeleteDialogComponent {
    lookupType: ILookupType;

    constructor(
        protected lookupTypeService: LookupTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lookupTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lookupTypeListModification',
                content: 'Deleted an lookupType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lookup-type-delete-popup',
    template: ''
})
export class LookupTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lookupType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LookupTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.lookupType = lookupType;
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
