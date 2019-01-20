import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRentRoll } from 'app/shared/model/rent-roll.model';
import { RentRollService } from './rent-roll.service';

@Component({
    selector: 'jhi-rent-roll-delete-dialog',
    templateUrl: './rent-roll-delete-dialog.component.html'
})
export class RentRollDeleteDialogComponent {
    rentRoll: IRentRoll;

    constructor(protected rentRollService: RentRollService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rentRollService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rentRollListModification',
                content: 'Deleted an rentRoll'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rent-roll-delete-popup',
    template: ''
})
export class RentRollDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rentRoll }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RentRollDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rentRoll = rentRoll;
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
