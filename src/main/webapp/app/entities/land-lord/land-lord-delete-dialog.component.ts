import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILandLord } from 'app/shared/model/land-lord.model';
import { LandLordService } from './land-lord.service';

@Component({
    selector: 'jhi-land-lord-delete-dialog',
    templateUrl: './land-lord-delete-dialog.component.html'
})
export class LandLordDeleteDialogComponent {
    landLord: ILandLord;

    constructor(protected landLordService: LandLordService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.landLordService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'landLordListModification',
                content: 'Deleted an landLord'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-land-lord-delete-popup',
    template: ''
})
export class LandLordDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ landLord }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LandLordDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.landLord = landLord;
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
