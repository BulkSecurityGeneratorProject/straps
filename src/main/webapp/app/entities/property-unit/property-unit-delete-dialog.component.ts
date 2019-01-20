import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropertyUnit } from 'app/shared/model/property-unit.model';
import { PropertyUnitService } from './property-unit.service';

@Component({
    selector: 'jhi-property-unit-delete-dialog',
    templateUrl: './property-unit-delete-dialog.component.html'
})
export class PropertyUnitDeleteDialogComponent {
    propertyUnit: IPropertyUnit;

    constructor(
        protected propertyUnitService: PropertyUnitService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.propertyUnitService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'propertyUnitListModification',
                content: 'Deleted an propertyUnit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-property-unit-delete-popup',
    template: ''
})
export class PropertyUnitDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ propertyUnit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PropertyUnitDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.propertyUnit = propertyUnit;
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
