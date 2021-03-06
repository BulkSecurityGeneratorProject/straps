/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { PropertyVendorDeleteDialogComponent } from 'app/entities/property-vendor/property-vendor-delete-dialog.component';
import { PropertyVendorService } from 'app/entities/property-vendor/property-vendor.service';

describe('Component Tests', () => {
    describe('PropertyVendor Management Delete Component', () => {
        let comp: PropertyVendorDeleteDialogComponent;
        let fixture: ComponentFixture<PropertyVendorDeleteDialogComponent>;
        let service: PropertyVendorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [PropertyVendorDeleteDialogComponent]
            })
                .overrideTemplate(PropertyVendorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PropertyVendorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyVendorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
