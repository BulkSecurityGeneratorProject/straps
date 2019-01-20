import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingPlan } from 'app/shared/model/billing-plan.model';
import { BillingPlanService } from './billing-plan.service';

@Component({
    selector: 'jhi-billing-plan-delete-dialog',
    templateUrl: './billing-plan-delete-dialog.component.html'
})
export class BillingPlanDeleteDialogComponent {
    billingPlan: IBillingPlan;

    constructor(
        protected billingPlanService: BillingPlanService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billingPlanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'billingPlanListModification',
                content: 'Deleted an billingPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-billing-plan-delete-popup',
    template: ''
})
export class BillingPlanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billingPlan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BillingPlanDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.billingPlan = billingPlan;
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
