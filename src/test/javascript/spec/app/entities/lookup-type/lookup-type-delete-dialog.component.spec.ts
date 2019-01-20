/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { LookupTypeDeleteDialogComponent } from 'app/entities/lookup-type/lookup-type-delete-dialog.component';
import { LookupTypeService } from 'app/entities/lookup-type/lookup-type.service';

describe('Component Tests', () => {
    describe('LookupType Management Delete Component', () => {
        let comp: LookupTypeDeleteDialogComponent;
        let fixture: ComponentFixture<LookupTypeDeleteDialogComponent>;
        let service: LookupTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [LookupTypeDeleteDialogComponent]
            })
                .overrideTemplate(LookupTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LookupTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LookupTypeService);
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
