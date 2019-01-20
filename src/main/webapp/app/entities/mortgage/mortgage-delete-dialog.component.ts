import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMortgage } from 'app/shared/model/mortgage.model';
import { MortgageService } from './mortgage.service';

@Component({
    selector: 'jhi-mortgage-delete-dialog',
    templateUrl: './mortgage-delete-dialog.component.html'
})
export class MortgageDeleteDialogComponent {
    mortgage: IMortgage;

    constructor(protected mortgageService: MortgageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mortgageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mortgageListModification',
                content: 'Deleted an mortgage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mortgage-delete-popup',
    template: ''
})
export class MortgageDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mortgage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MortgageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mortgage = mortgage;
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
