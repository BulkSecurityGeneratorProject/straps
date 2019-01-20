/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PromalyV5TestModule } from '../../../test.module';
import { PropertyUnitDeleteDialogComponent } from 'app/entities/property-unit/property-unit-delete-dialog.component';
import { PropertyUnitService } from 'app/entities/property-unit/property-unit.service';

describe('Component Tests', () => {
    describe('PropertyUnit Management Delete Component', () => {
        let comp: PropertyUnitDeleteDialogComponent;
        let fixture: ComponentFixture<PropertyUnitDeleteDialogComponent>;
        let service: PropertyUnitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [PropertyUnitDeleteDialogComponent]
            })
                .overrideTemplate(PropertyUnitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PropertyUnitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyUnitService);
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
