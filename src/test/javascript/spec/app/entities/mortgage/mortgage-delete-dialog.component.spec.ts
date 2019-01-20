/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { MortgageDeleteDialogComponent } from 'app/entities/mortgage/mortgage-delete-dialog.component';
import { MortgageService } from 'app/entities/mortgage/mortgage.service';

describe('Component Tests', () => {
    describe('Mortgage Management Delete Component', () => {
        let comp: MortgageDeleteDialogComponent;
        let fixture: ComponentFixture<MortgageDeleteDialogComponent>;
        let service: MortgageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [MortgageDeleteDialogComponent]
            })
                .overrideTemplate(MortgageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MortgageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MortgageService);
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
