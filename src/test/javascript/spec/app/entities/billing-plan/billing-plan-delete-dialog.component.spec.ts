/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { BillingPlanDeleteDialogComponent } from 'app/entities/billing-plan/billing-plan-delete-dialog.component';
import { BillingPlanService } from 'app/entities/billing-plan/billing-plan.service';

describe('Component Tests', () => {
    describe('BillingPlan Management Delete Component', () => {
        let comp: BillingPlanDeleteDialogComponent;
        let fixture: ComponentFixture<BillingPlanDeleteDialogComponent>;
        let service: BillingPlanService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [BillingPlanDeleteDialogComponent]
            })
                .overrideTemplate(BillingPlanDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillingPlanDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingPlanService);
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
