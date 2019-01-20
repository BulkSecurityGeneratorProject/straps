/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { IncomeProjectionDeleteDialogComponent } from 'app/entities/income-projection/income-projection-delete-dialog.component';
import { IncomeProjectionService } from 'app/entities/income-projection/income-projection.service';

describe('Component Tests', () => {
    describe('IncomeProjection Management Delete Component', () => {
        let comp: IncomeProjectionDeleteDialogComponent;
        let fixture: ComponentFixture<IncomeProjectionDeleteDialogComponent>;
        let service: IncomeProjectionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [IncomeProjectionDeleteDialogComponent]
            })
                .overrideTemplate(IncomeProjectionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncomeProjectionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncomeProjectionService);
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
