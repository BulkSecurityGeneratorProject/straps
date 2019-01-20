import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropertyVendor } from 'app/shared/model/property-vendor.model';
import { PropertyVendorService } from './property-vendor.service';

@Component({
    selector: 'jhi-property-vendor-delete-dialog',
    templateUrl: './property-vendor-delete-dialog.component.html'
})
export class PropertyVendorDeleteDialogComponent {
    propertyVendor: IPropertyVendor;

    constructor(
        protected propertyVendorService: PropertyVendorService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.propertyVendorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'propertyVendorListModification',
                content: 'Deleted an propertyVendor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-property-vendor-delete-popup',
    template: ''
})
export class PropertyVendorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ propertyVendor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PropertyVendorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.propertyVendor = propertyVendor;
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
