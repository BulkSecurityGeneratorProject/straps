import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiscountPlan } from 'app/shared/model/discount-plan.model';
import { DiscountPlanService } from './discount-plan.service';

@Component({
    selector: 'jhi-discount-plan-delete-dialog',
    templateUrl: './discount-plan-delete-dialog.component.html'
})
export class DiscountPlanDeleteDialogComponent {
    discountPlan: IDiscountPlan;

    constructor(
        protected discountPlanService: DiscountPlanService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.discountPlanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'discountPlanListModification',
                content: 'Deleted an discountPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-discount-plan-delete-popup',
    template: ''
})
export class DiscountPlanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ discountPlan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DiscountPlanDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.discountPlan = discountPlan;
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
