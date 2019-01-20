import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncomeProjection } from 'app/shared/model/income-projection.model';
import { IncomeProjectionService } from './income-projection.service';

@Component({
    selector: 'jhi-income-projection-delete-dialog',
    templateUrl: './income-projection-delete-dialog.component.html'
})
export class IncomeProjectionDeleteDialogComponent {
    incomeProjection: IIncomeProjection;

    constructor(
        protected incomeProjectionService: IncomeProjectionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incomeProjectionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'incomeProjectionListModification',
                content: 'Deleted an incomeProjection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-income-projection-delete-popup',
    template: ''
})
export class IncomeProjectionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incomeProjection }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncomeProjectionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.incomeProjection = incomeProjection;
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
