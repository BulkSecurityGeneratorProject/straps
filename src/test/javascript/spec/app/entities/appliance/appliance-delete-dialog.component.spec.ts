/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { ApplianceDeleteDialogComponent } from 'app/entities/appliance/appliance-delete-dialog.component';
import { ApplianceService } from 'app/entities/appliance/appliance.service';

describe('Component Tests', () => {
    describe('Appliance Management Delete Component', () => {
        let comp: ApplianceDeleteDialogComponent;
        let fixture: ComponentFixture<ApplianceDeleteDialogComponent>;
        let service: ApplianceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [ApplianceDeleteDialogComponent]
            })
                .overrideTemplate(ApplianceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplianceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplianceService);
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
