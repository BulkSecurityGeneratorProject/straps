import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyCapability } from 'app/shared/model/company-capability.model';
import { CompanyCapabilityService } from './company-capability.service';

@Component({
    selector: 'jhi-company-capability-delete-dialog',
    templateUrl: './company-capability-delete-dialog.component.html'
})
export class CompanyCapabilityDeleteDialogComponent {
    companyCapability: ICompanyCapability;

    constructor(
        protected companyCapabilityService: CompanyCapabilityService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyCapabilityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'companyCapabilityListModification',
                content: 'Deleted an companyCapability'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-capability-delete-popup',
    template: ''
})
export class CompanyCapabilityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ companyCapability }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompanyCapabilityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.companyCapability = companyCapability;
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
